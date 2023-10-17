

package org.apache.rocketmq.acl.plain;

import java.util.Map;
import org.apache.rocketmq.acl.AccessResource;
import org.apache.rocketmq.acl.PermissionChecker;
import org.apache.rocketmq.acl.common.AclException;
import org.apache.rocketmq.acl.common.Permission;

public class PlainPermissionChecker implements PermissionChecker {
    public void check(AccessResource checkedAccess, AccessResource ownedAccess) {
        PlainAccessResource checkedPlainAccess = (PlainAccessResource) checkedAccess;
        PlainAccessResource ownedPlainAccess = (PlainAccessResource) ownedAccess;
        if (Permission.needAdminPerm(checkedPlainAccess.getRequestCode()) && !ownedPlainAccess.isAdmin()) {
            throw new AclException(String.format("Need admin permission for request code=%d, but accessKey=%s is not", checkedPlainAccess.getRequestCode(), ownedPlainAccess.getAccessKey()));
        }
        Map<String, Byte> needCheckedPermMap = checkedPlainAccess.getResourcePermMap();
        Map<String, Byte> ownedPermMap = ownedPlainAccess.getResourcePermMap();

        if (needCheckedPermMap == null) {
            // If the needCheckedPermMap is null,then return
            return;
        }

        if (ownedPermMap == null && ownedPlainAccess.isAdmin()) {
            // If the ownedPermMap is null and it is an admin user, then return
            return;
        }

        for (Map.Entry<String, Byte> needCheckedEntry : needCheckedPermMap.entrySet()) {
            String resource = needCheckedEntry.getKey();
            Byte neededPerm = needCheckedEntry.getValue();
            boolean isGroup = PlainAccessResource.isRetryTopic(resource);

            if (ownedPermMap == null || !ownedPermMap.containsKey(resource)) {
                // Check the default perm
                byte ownedPerm = isGroup ? ownedPlainAccess.getDefaultGroupPerm() :
                    ownedPlainAccess.getDefaultTopicPerm();
                if (!Permission.checkPermission(neededPerm, ownedPerm)) {
                    throw new AclException(String.format("No default permission for %s", PlainAccessResource.printStr(resource, isGroup)));
                }
                continue;
            }
            if (!Permission.checkPermission(neededPerm, ownedPermMap.get(resource))) {
                throw new AclException(String.format("No default permission for %s", PlainAccessResource.printStr(resource, isGroup)));
            }
        }
    }
}
