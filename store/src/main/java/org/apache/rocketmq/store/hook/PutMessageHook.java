
package org.apache.rocketmq.store.hook;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.store.PutMessageResult;

public interface PutMessageHook {

    /**
     * Name of the hook.
     *
     * @return name of the hook
     */
    String hookName();

    /**
     *  Execute before put message. For example, Message verification or special message transform
     * @param msg
     * @return
     */
    PutMessageResult executeBeforePutMessage(MessageExt msg);
}
