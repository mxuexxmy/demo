package edu.gues.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <h2> DateRange 时间随机对象 </h2>
 *
 * @author mxuexxmy
 * @date 2022/3/5 14:12
 */
public final class DateRange {
    private final LocalDate start;
    private final LocalDate end;

    public DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return this.start;
    }
    public LocalDate getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE, MMM d, uuuu");
        return dateFormat.format(this.start) + " - " + dateFormat.format(this.end);
    }
}
