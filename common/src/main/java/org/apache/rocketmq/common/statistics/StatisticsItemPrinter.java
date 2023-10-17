
package org.apache.rocketmq.common.statistics;

import org.apache.rocketmq.logging.InternalLogger;

public class StatisticsItemPrinter {
    private InternalLogger log;

    private StatisticsItemFormatter formatter;

    public StatisticsItemPrinter(StatisticsItemFormatter formatter, InternalLogger log) {
        this.formatter = formatter;
        this.log = log;
    }

    public void log(InternalLogger log) {
        this.log = log;
    }

    public void formatter(StatisticsItemFormatter formatter) {
        this.formatter = formatter;
    }

    public void print(String prefix, StatisticsItem statItem, String... suffixs) {
        StringBuilder suffix = new StringBuilder();
        for (String str : suffixs) {
            suffix.append(str);
        }

        if (log != null) {
            log.info("{}{}{}", prefix, formatter.format(statItem), suffix.toString());
        }
        // System.out.printf("%s %s%s%s\n", new Date().toString(), prefix, formatter.format(statItem), suffix.toString());
    }
}
