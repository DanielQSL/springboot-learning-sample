package com.qsl.jmh;

import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用来判断字符串分割效率
 *
 * @author DanielQSL
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
public class StringSplitTest {

    private static final String TEXT = "说的发懂法守法是,,非得失飞洒发,,送范德萨发防守,,打法范德萨发范德萨,,发士大夫算法,,算法发";

    private static final String SEPARATOR = ",,";

    @Benchmark
    public void testOriginal(Blackhole blackhole) {
        String[] split = TEXT.split(SEPARATOR);

        // 避免 JIT 的优化消除
        blackhole.consume(split);
    }

    @Benchmark
    public void testCustom(Blackhole blackhole) {
        List<String> split = split(TEXT, SEPARATOR);

        // 避免 JIT 的优化消除
        blackhole.consume(split);
    }

    public static List<String> split(String str, final String separatorChars) {
        if (null == str) {
            return Collections.emptyList();
        }

        if (StringUtils.isEmpty(separatorChars)) {
            return Collections.singletonList(str);
        }

        final List<String> strList = new ArrayList<>();
        while (true) {
            int index = str.indexOf(separatorChars);
            if (index < 0) {
                strList.add(str);
                break;
            }
            strList.add(str.substring(0, index));
            str = str.substring(index + separatorChars.length());
        }
        return strList;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringSplitTest.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

}
