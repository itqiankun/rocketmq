

package org.apache.rocketmq.broker.filter;

import org.apache.rocketmq.filter.expression.EvaluationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Evaluation context from message.
 */
public class MessageEvaluationContext implements EvaluationContext {

    private Map<String, String> properties;

    public MessageEvaluationContext(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public Object get(final String name) {
        if (this.properties == null) {
            return null;
        }
        return this.properties.get(name);
    }

    @Override
    public Map<String, Object> keyValues() {
        if (properties == null) {
            return null;
        }

        Map<String, Object> copy = new HashMap<String, Object>(properties.size(), 1);

        for (Entry<String, String> entry : properties.entrySet()) {
            copy.put(entry.getKey(), entry.getValue());
        }

        return copy;
    }
}
