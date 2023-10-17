

package org.apache.rocketmq.broker.plugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import org.apache.rocketmq.store.MessageStore;

public final class MessageStoreFactory {
    public final static MessageStore build(MessageStorePluginContext context,
        MessageStore messageStore) throws IOException {
        String plugin = context.getBrokerConfig().getMessageStorePlugIn();
        if (plugin != null && plugin.trim().length() != 0) {
            String[] pluginClasses = plugin.split(",");
            for (int i = pluginClasses.length - 1; i >= 0; --i) {
                String pluginClass = pluginClasses[i];
                try {
                    @SuppressWarnings("unchecked")
                    Class<AbstractPluginMessageStore> clazz = (Class<AbstractPluginMessageStore>)Class.forName(pluginClass);
                    Constructor<AbstractPluginMessageStore> construct = clazz.getConstructor(MessageStorePluginContext.class, MessageStore.class);
                    AbstractPluginMessageStore pluginMessageStore = construct.newInstance(context, messageStore);
                    messageStore = pluginMessageStore;
                }
                catch (Throwable e) {
                    throw new RuntimeException("Initialize plugin's class: " + pluginClass + " not found!", e);
                }
            }
        }
        return messageStore;
    }
}
