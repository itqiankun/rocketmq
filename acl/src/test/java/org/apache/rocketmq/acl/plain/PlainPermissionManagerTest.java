
package org.apache.rocketmq.acl.plain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.rocketmq.acl.common.AclConstants;
import org.apache.rocketmq.acl.common.AclException;
import org.apache.rocketmq.acl.common.AclUtils;
import org.apache.rocketmq.acl.common.Permission;
import org.apache.rocketmq.common.AclConfig;
import org.apache.rocketmq.common.DataVersion;
import org.apache.rocketmq.common.PlainAccessConfig;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class PlainPermissionManagerTest {

    PlainPermissionManager plainPermissionManager;
    PlainAccessResource PUBPlainAccessResource;
    PlainAccessResource SUBPlainAccessResource;
    PlainAccessResource ANYPlainAccessResource;
    PlainAccessResource DENYPlainAccessResource;
    PlainAccessResource plainAccessResource = new PlainAccessResource();
    PlainAccessConfig plainAccessConfig = new PlainAccessConfig();
    Set<Integer> adminCode = new HashSet<>();

    private static final String PATH = PlainPermissionManagerTest.class.getResource(File.separator).getFile();

    private static final String DEFAULT_TOPIC = "topic-acl";

    @Before
    public void init() throws NoSuchFieldException, SecurityException, IOException {
        // UPDATE_AND_CREATE_TOPIC
        adminCode.add(17);
        // UPDATE_BROKER_CONFIG
        adminCode.add(25);
        // DELETE_TOPIC_IN_BROKER
        adminCode.add(215);
        // UPDATE_AND_CREATE_SUBSCRIPTIONGROUP
        adminCode.add(200);
        // DELETE_SUBSCRIPTIONGROUP
        adminCode.add(207);

        PUBPlainAccessResource = clonePlainAccessResource(Permission.PUB);
        SUBPlainAccessResource = clonePlainAccessResource(Permission.SUB);
        ANYPlainAccessResource = clonePlainAccessResource(Permission.ANY);
        DENYPlainAccessResource = clonePlainAccessResource(Permission.DENY);

        File file = new File(PATH);
        System.setProperty("rocketmq.home.dir", file.getAbsolutePath());

        plainPermissionManager = new PlainPermissionManager();

    }

    public PlainAccessResource clonePlainAccessResource(byte perm) {
        PlainAccessResource painAccessResource = new PlainAccessResource();
        painAccessResource.setAccessKey("RocketMQ");
        painAccessResource.setSecretKey("12345678");
        painAccessResource.setWhiteRemoteAddress("127.0." + perm + ".*");
        painAccessResource.setDefaultGroupPerm(perm);
        painAccessResource.setDefaultTopicPerm(perm);
        painAccessResource.addResourceAndPerm(PlainAccessResource.getRetryTopic("groupA"), Permission.PUB);
        painAccessResource.addResourceAndPerm(PlainAccessResource.getRetryTopic("groupB"), Permission.SUB);
        painAccessResource.addResourceAndPerm(PlainAccessResource.getRetryTopic("groupC"), Permission.ANY);
        painAccessResource.addResourceAndPerm(PlainAccessResource.getRetryTopic("groupD"), Permission.DENY);

        painAccessResource.addResourceAndPerm("topicA", Permission.PUB);
        painAccessResource.addResourceAndPerm("topicB", Permission.SUB);
        painAccessResource.addResourceAndPerm("topicC", Permission.ANY);
        painAccessResource.addResourceAndPerm("topicD", Permission.DENY);
        return painAccessResource;
    }

    @Test
    public void buildPlainAccessResourceTest() {
        PlainAccessResource plainAccessResource = null;
        PlainAccessConfig plainAccess = new PlainAccessConfig();

        plainAccess.setAccessKey("RocketMQ");
        plainAccess.setSecretKey("12345678");
        plainAccessResource = plainPermissionManager.buildPlainAccessResource(plainAccess);
        Assert.assertEquals(plainAccessResource.getAccessKey(), "RocketMQ");
        Assert.assertEquals(plainAccessResource.getSecretKey(), "12345678");

        plainAccess.setWhiteRemoteAddress("127.0.0.1");
        plainAccessResource = plainPermissionManager.buildPlainAccessResource(plainAccess);
        Assert.assertEquals(plainAccessResource.getWhiteRemoteAddress(), "127.0.0.1");

        plainAccess.setAdmin(true);
        plainAccessResource = plainPermissionManager.buildPlainAccessResource(plainAccess);
        Assert.assertEquals(plainAccessResource.isAdmin(), true);

        List<String> groups = new ArrayList<String>();
        groups.add("groupA=DENY");
        groups.add("groupB=PUB|SUB");
        groups.add("groupC=PUB");
        plainAccess.setGroupPerms(groups);
        plainAccessResource = plainPermissionManager.buildPlainAccessResource(plainAccess);
        Map<String, Byte> resourcePermMap = plainAccessResource.getResourcePermMap();
        Assert.assertEquals(resourcePermMap.size(), 3);

        Assert.assertEquals(resourcePermMap.get(PlainAccessResource.getRetryTopic("groupA")).byteValue(), Permission.DENY);
        Assert.assertEquals(resourcePermMap.get(PlainAccessResource.getRetryTopic("groupB")).byteValue(), Permission.PUB | Permission.SUB);
        Assert.assertEquals(resourcePermMap.get(PlainAccessResource.getRetryTopic("groupC")).byteValue(), Permission.PUB);

        List<String> topics = new ArrayList<String>();
        topics.add("topicA=DENY");
        topics.add("topicB=PUB|SUB");
        topics.add("topicC=PUB");
        plainAccess.setTopicPerms(topics);
        plainAccessResource = plainPermissionManager.buildPlainAccessResource(plainAccess);
        resourcePermMap = plainAccessResource.getResourcePermMap();
        Assert.assertEquals(resourcePermMap.size(), 6);

        Assert.assertEquals(resourcePermMap.get("topicA").byteValue(), Permission.DENY);
        Assert.assertEquals(resourcePermMap.get("topicB").byteValue(), Permission.PUB | Permission.SUB);
        Assert.assertEquals(resourcePermMap.get("topicC").byteValue(), Permission.PUB);
    }

    @Test(expected = AclException.class)
    public void checkPermAdmin() {
        PlainAccessResource plainAccessResource = new PlainAccessResource();
        plainAccessResource.setRequestCode(17);
        plainPermissionManager.checkPerm(plainAccessResource, PUBPlainAccessResource);
    }

    @Test
    public void checkPerm() {

        PlainAccessResource plainAccessResource = new PlainAccessResource();
        plainAccessResource.addResourceAndPerm("topicA", Permission.PUB);
        plainPermissionManager.checkPerm(plainAccessResource, PUBPlainAccessResource);
        plainAccessResource.addResourceAndPerm("topicB", Permission.SUB);
        plainPermissionManager.checkPerm(plainAccessResource, ANYPlainAccessResource);

        plainAccessResource = new PlainAccessResource();
        plainAccessResource.addResourceAndPerm("topicB", Permission.SUB);
        plainPermissionManager.checkPerm(plainAccessResource, SUBPlainAccessResource);
        plainAccessResource.addResourceAndPerm("topicA", Permission.PUB);
        plainPermissionManager.checkPerm(plainAccessResource, ANYPlainAccessResource);

    }

    @Test(expected = AclException.class)
    public void checkErrorPermDefaultValueNotMatch() {

        plainAccessResource = new PlainAccessResource();
        plainAccessResource.addResourceAndPerm("topicF", Permission.PUB);
        plainPermissionManager.checkPerm(plainAccessResource, SUBPlainAccessResource);
    }

    @Test(expected = AclException.class)
    public void accountNullTest() {
        plainAccessConfig.setAccessKey(null);
        plainPermissionManager.buildPlainAccessResource(plainAccessConfig);
    }

    @Test(expected = AclException.class)
    public void accountThanTest() {
        plainAccessConfig.setAccessKey("123");
        plainPermissionManager.buildPlainAccessResource(plainAccessConfig);
    }

    @Test(expected = AclException.class)
    public void passWordtNullTest() {
        plainAccessConfig.setAccessKey(null);
        plainPermissionManager.buildPlainAccessResource(plainAccessConfig);
    }

    @Test(expected = AclException.class)
    public void passWordThanTest() {
        plainAccessConfig.setSecretKey("123");
        plainPermissionManager.buildPlainAccessResource(plainAccessConfig);
    }


    @SuppressWarnings("unchecked")
    @Test
    public void cleanAuthenticationInfoTest() throws IllegalAccessException {
        // PlainPermissionManager.addPlainAccessResource(plainAccessResource);
        Map<String, Map<String, PlainAccessResource>> plainAccessResourceMap = (Map<String, Map<String, PlainAccessResource>>) FieldUtils.readDeclaredField(plainPermissionManager, "aclPlainAccessResourceMap", true);
        Assert.assertFalse(plainAccessResourceMap.isEmpty());

        plainPermissionManager.clearPermissionInfo();
        plainAccessResourceMap = (Map<String, Map<String, PlainAccessResource>>) FieldUtils.readDeclaredField(plainPermissionManager, "aclPlainAccessResourceMap", true);
        Assert.assertTrue(plainAccessResourceMap.isEmpty());
        // RemoveDataVersionFromYamlFile("src/test/resources/conf/plain_acl.yml");
    }

    @Test
    public void isWatchStartTest() {

        PlainPermissionManager plainPermissionManager = new PlainPermissionManager();
        Assert.assertTrue(plainPermissionManager.isWatchStart());
        // RemoveDataVersionFromYamlFile("src/test/resources/conf/plain_acl.yml");
    }

    @Test
    public void multiFilePathTest() {
        File file = new File(PATH);
        System.setProperty("rocketmq.home.dir", file.getAbsolutePath());

        PlainPermissionManager plainPermissionManager = new PlainPermissionManager();

        String samefilePath = file.getAbsolutePath()+"/conf/acl/.";
        String samefilePath2 = "/" +file.getAbsolutePath()+"/conf/acl";
        String samefilePath3 = file.getAbsolutePath()+"/conf/acl/../"+file.getAbsolutePath();
        String samefilePath4 = file.getAbsolutePath()+"/conf/acl///";
        String samefilePath5 = file.getAbsolutePath()+"/conf/acl/./";

        int size = plainPermissionManager.getDataVersionMap().size();

        plainPermissionManager.load(samefilePath);
        Assert.assertEquals(size, plainPermissionManager.getDataVersionMap().size());

        plainPermissionManager.load(samefilePath2);
        Assert.assertEquals(size, plainPermissionManager.getDataVersionMap().size());

        plainPermissionManager.load(samefilePath3);
        Assert.assertEquals(size, plainPermissionManager.getDataVersionMap().size());

        plainPermissionManager.load(samefilePath4);
        Assert.assertEquals(size, plainPermissionManager.getDataVersionMap().size());

        plainPermissionManager.load(samefilePath5);
        Assert.assertEquals(size, plainPermissionManager.getDataVersionMap().size());

    }

    @Test
    public void testWatch() throws IOException, IllegalAccessException, InterruptedException {
        File file = new File(PATH);
        System.setProperty("rocketmq.home.dir", file.getAbsolutePath());

        String fileName = System.getProperty("rocketmq.home.dir") + File.separator + "/conf/acl/plain_acl_test.yml";
        File transport = new File(fileName);
        transport.delete();
        transport.createNewFile();
        FileWriter writer = new FileWriter(transport);
        writer.write("accounts:\r\n");
        writer.write("- accessKey: watchrocketmqx\r\n");
        writer.write("  secretKey: 12345678\r\n");
        writer.write("  whiteRemoteAddress: 127.0.0.1\r\n");
        writer.write("  admin: true\r\n");
        writer.flush();
        writer.close();

        Thread.sleep(1000);

        PlainPermissionManager plainPermissionManager = new PlainPermissionManager();
        Assert.assertTrue(plainPermissionManager.isWatchStart());

        Map<String, String> accessKeyTable = (Map<String, String>) FieldUtils.readDeclaredField(plainPermissionManager, "accessKeyTable", true);
        String aclFileName = accessKeyTable.get("watchrocketmqx");
        {
            Map<String, Map<String, PlainAccessResource>> plainAccessResourceMap = (Map<String, Map<String, PlainAccessResource>>) FieldUtils.readDeclaredField(plainPermissionManager, "aclPlainAccessResourceMap", true);
            PlainAccessResource accessResource = plainAccessResourceMap.get(aclFileName).get("watchrocketmqx");
            Assert.assertNotNull(accessResource);
            Assert.assertEquals(accessResource.getSecretKey(), "12345678");
            Assert.assertTrue(accessResource.isAdmin());

        }

        Map<String, Object> updatedMap = AclUtils.getYamlDataObject(fileName, Map.class);
        List<Map<String, Object>> accounts = (List<Map<String, Object>>) updatedMap.get("accounts");
        accounts.get(0).remove("accessKey");
        accounts.get(0).remove("secretKey");
        accounts.get(0).put("accessKey", "watchrocketmq1y");
        accounts.get(0).put("secretKey", "88888888");
        accounts.get(0).put("admin", "false");
        // Update file and flush to yaml file
        AclUtils.writeDataObject(fileName, updatedMap);

        Thread.sleep(10000);
        {
            Map<String, Map<String, PlainAccessResource>> plainAccessResourceMap = (Map<String, Map<String, PlainAccessResource>>) FieldUtils.readDeclaredField(plainPermissionManager, "aclPlainAccessResourceMap", true);
            PlainAccessResource accessResource = plainAccessResourceMap.get(aclFileName).get("watchrocketmq1y");
            Assert.assertNotNull(accessResource);
            Assert.assertEquals(accessResource.getSecretKey(), "88888888");
            Assert.assertFalse(accessResource.isAdmin());

        }
        transport.delete();
        System.setProperty("rocketmq.home.dir", PATH);
    }
    
    @Test
    public void updateAccessConfigTest() {
        Assert.assertThrows(AclException.class, () -> plainPermissionManager.updateAccessConfig(null));
        
        plainAccessConfig.setAccessKey("admin_test");
        // Invalid parameter
        plainAccessConfig.setSecretKey("123456");
        plainAccessConfig.setAdmin(true);
        Assert.assertThrows(AclException.class, () -> plainPermissionManager.updateAccessConfig(plainAccessConfig));
        
        plainAccessConfig.setSecretKey("12345678");
        // Invalid parameter
        plainAccessConfig.setGroupPerms(Lists.newArrayList("groupA!SUB"));
        Assert.assertThrows(AclException.class, () -> plainPermissionManager.updateAccessConfig(plainAccessConfig));
        
        // first update
        plainAccessConfig.setGroupPerms(Lists.newArrayList("groupA=SUB"));
        plainPermissionManager.updateAccessConfig(plainAccessConfig);
        
        // second update
        plainAccessConfig.setTopicPerms(Lists.newArrayList("topicA=SUB"));
        plainPermissionManager.updateAccessConfig(plainAccessConfig);
    }

    @Test
    public void getAllAclFilesTest() {
        final List<String> notExistList = plainPermissionManager.getAllAclFiles("aa/bb");
        Assertions.assertThat(notExistList).isEmpty();
        final List<String> files = plainPermissionManager.getAllAclFiles(PATH);
        Assertions.assertThat(files).isNotEmpty();
    }

    @Test
    public void loadTest() {
        plainPermissionManager.load();
        final Map<String, DataVersion> map = plainPermissionManager.getDataVersionMap();
        Assertions.assertThat(map).isNotEmpty();
    }

    @Test
    public void updateAclConfigFileVersionTest() {
        String aclFileName = "test_plain_acl";
        Map<String, Object> updateAclConfigMap  = new HashMap<>();
        List<Map<String, Object>> versionElement = new ArrayList<>();
        Map<String, Object> accountsMap = new LinkedHashMap<>();
        accountsMap.put(AclConstants.CONFIG_COUNTER, 1);
        accountsMap.put(AclConstants.CONFIG_TIME_STAMP, System.currentTimeMillis());
        versionElement.add(accountsMap);

        updateAclConfigMap.put(AclConstants.CONFIG_DATA_VERSION, versionElement);
        final Map<String, Object> map = plainPermissionManager.updateAclConfigFileVersion(aclFileName, updateAclConfigMap);
        final List<Map<String, Object>> version = (List<Map<String, Object>>) map.get("dataVersion");
        Assertions.assertThat(map).isNotEmpty();
        Assert.assertEquals(2L, version.get(0).get("counter"));
    }

    @Test
    public void createAclAccessConfigMapTest() {
        Map<String, Object> existedAccountMap =  new HashMap<>();
        plainAccessConfig.setAccessKey("admin123");
        plainAccessConfig.setSecretKey("12345678");
        plainAccessConfig.setWhiteRemoteAddress("192.168.1.1");
        plainAccessConfig.setAdmin(false);
        plainAccessConfig.setDefaultGroupPerm(AclConstants.SUB_PUB);
        plainAccessConfig.setTopicPerms(Arrays.asList(DEFAULT_TOPIC + "=" + AclConstants.PUB));
        plainAccessConfig.setGroupPerms(Lists.newArrayList("groupA=SUB"));

        final Map<String, Object> map = plainPermissionManager.createAclAccessConfigMap(existedAccountMap, plainAccessConfig);
        Assertions.assertThat(map).isNotEmpty();
        Assert.assertEquals(AclConstants.SUB_PUB, map.get("defaultGroupPerm"));
        final List groupPerms = (List) map.get("groupPerms");
        Assert.assertEquals("groupA=SUB", groupPerms.get(0));
        Assert.assertEquals("12345678", map.get("secretKey"));
        Assert.assertEquals("admin123", map.get("accessKey"));
        Assert.assertEquals("192.168.1.1", map.get("whiteRemoteAddress"));
        final List topicPerms = (List) map.get("topicPerms");
        Assert.assertEquals("topic-acl=PUB", topicPerms.get(0));
        Assert.assertEquals(false, map.get("admin"));
    }

    @Test
    public void deleteAccessConfigTest() throws InterruptedException {
        // delete not exist accessConfig
        final boolean flag1 = plainPermissionManager.deleteAccessConfig("test_delete");
        assert flag1 == false;

        plainAccessConfig.setAccessKey("test_delete");
        plainAccessConfig.setSecretKey("12345678");
        plainAccessConfig.setWhiteRemoteAddress("192.168.1.1");
        plainAccessConfig.setAdmin(false);
        plainAccessConfig.setDefaultGroupPerm(AclConstants.SUB_PUB);
        plainAccessConfig.setTopicPerms(Arrays.asList(DEFAULT_TOPIC + "=" + AclConstants.PUB));
        plainAccessConfig.setGroupPerms(Lists.newArrayList("groupA=SUB"));
        plainPermissionManager.updateAccessConfig(plainAccessConfig);

        //delete existed accessConfig
        final boolean flag2 = plainPermissionManager.deleteAccessConfig("test_delete");
        assert flag2 == true;

    }

    @Test
    public void updateGlobalWhiteAddrsConfigTest() {
        final boolean flag = plainPermissionManager.updateGlobalWhiteAddrsConfig(Lists.newArrayList("192.168.1.2"));
        assert flag == true;
        final AclConfig config = plainPermissionManager.getAllAclConfig();
        Assert.assertEquals(true, config.getGlobalWhiteAddrs().contains("192.168.1.2"));
    }

}
