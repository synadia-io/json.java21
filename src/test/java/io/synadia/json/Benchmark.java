// Copyright 2025 Synadia Communications, Inc.
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.synadia.json;

import io.nats.client.api.ConsumerConfiguration;
import io.nats.client.api.StreamConfiguration;
import io.nats.client.api.StreamInfo;

import java.text.NumberFormat;
import java.util.Locale;

import static io.ResourceUtils.resourceAsBytes;

public final class Benchmark {
    static final String RESULTS_HEADER = "                       | Elapsed ms      | Rounds       | Rounds/Time    | Time/Round        |\n";
    static final String RESULTS_SEP    = "-----------------------|-----------------|--------------|----------------|-------------------|\n";
    static final String RESULTS = "%-22s | %15s | %12s | %14s | %17s |\n";

    static byte[] JSON_SI = resourceAsBytes("stream-info.json");
    static byte[] JSON_CC = resourceAsBytes("consumer-configuration.json");
    static JsonValue JV_SI = JsonParser.parseUnchecked(JSON_SI);
    static JsonValue JV_CC = JsonParser.parseUnchecked(JSON_CC);
    static StreamInfo SI = new StreamInfo(JV_SI);
    static StreamConfiguration SC = SI.getConfiguration();
    static ConsumerConfiguration CC = ConsumerConfiguration.builder().jsonValue(JV_CC).build();

    static long ROUNDS = 10_000_000;
    static long REPORT_FREQUENCY = ROUNDS / 100;
    static long NUM_DIFF_BENCHES = 7;

    public static void main(String[] args) {
        long totalElapsed = 0;
        long benchParseSiElapsed = 0;
        long benchParseCcElapsed = 0;
        long benchCreateSiElapsed = 0;
        long benchCreateCcElapsed = 0;
        long benchToJsonScElapsed = 0;
        long benchToJsonCcElapsed = 0;
        long benchUnitCoverage = 0;
        for (int r = 1; r <= ROUNDS; r++) {
            benchParseSiElapsed += benchParseSi();
            benchParseCcElapsed += benchParseCc();
            benchCreateSiElapsed += benchCreateSi();
            benchCreateCcElapsed += benchCreateCc();
            benchToJsonScElapsed += benchToJsonSc();
            benchToJsonCcElapsed += benchToJsonCc();
            benchUnitCoverage += BenchmarkUnitCoverage.unitCoverage();
            totalElapsed = benchParseSiElapsed + benchParseCcElapsed + benchCreateSiElapsed + benchCreateCcElapsed + benchToJsonScElapsed + benchToJsonCcElapsed + benchUnitCoverage;
            if (r % REPORT_FREQUENCY == 0) {
                printResults(r,
                    benchParseSiElapsed, benchParseCcElapsed,
                    benchCreateSiElapsed, benchCreateCcElapsed,
                    benchToJsonScElapsed, benchToJsonCcElapsed,
                    benchUnitCoverage,
                    totalElapsed);
            }
        }

        System.out.println("\n");
        printResults(ROUNDS,
            benchParseSiElapsed, benchParseCcElapsed,
            benchCreateSiElapsed, benchCreateCcElapsed,
            benchToJsonScElapsed, benchToJsonCcElapsed,
            benchUnitCoverage,
            totalElapsed);
    }

    private static void printResults(long rounds, long benchParseSiElapsed, long benchParseCcElapsed, long benchCreateSiElapsed, long benchCreateCcElapsed, long benchToJsonScElapsed, long benchToJsonCcElapsed, long benchUnitCoverage, long totalElapsed) {
        System.out.println();
        System.out.printf(RESULTS_HEADER + RESULTS_SEP);
        printResults("Parse Stream Info", benchParseSiElapsed, rounds);
        printResults("Parse Consumer Config", benchParseCcElapsed, rounds);
        printResults("Create Stream Info", benchCreateSiElapsed, rounds);
        printResults("Create Consumer Config", benchCreateCcElapsed, rounds);
        printResults("ToJson Stream Config", benchToJsonScElapsed, rounds);
        printResults("ToJson Consumer Config", benchToJsonCcElapsed, rounds);
        printResults("Unit Tests", benchUnitCoverage, rounds);
        System.out.printf(RESULTS_SEP);
        printResults("Total/Average", totalElapsed, rounds * NUM_DIFF_BENCHES);
    }

    // ----------------------------------------------------------------------------------------------------
    // BENCHMARKS
    // ----------------------------------------------------------------------------------------------------
    public static long benchParseSi() {
        long start = System.nanoTime();
        JsonParser.parseUnchecked(JSON_SI);
        return System.nanoTime() - start;
    }

    public static long benchParseCc() {
        long start = System.nanoTime();
        JsonParser.parseUnchecked(JSON_CC);
        return System.nanoTime() - start;
    }

    public static long benchCreateSi() {
        long start = System.nanoTime();
        new StreamInfo(JV_SI);
        return System.nanoTime() - start;
    }

    public static long benchCreateCc() {
        long start = System.nanoTime();
        ConsumerConfiguration.builder().jsonValue(JV_CC);
        return System.nanoTime() - start;
    }

    public static long benchToJsonSc() {
        long start = System.nanoTime();
        SC.toJson();
        return System.nanoTime() - start;
    }

    public static long benchToJsonCc() {
        long start = System.nanoTime();
        CC.toJson();
        return System.nanoTime() - start;
    }

    // ----------------------------------------------------------------------------------------------------
    // RESULT HELPERS
    // ----------------------------------------------------------------------------------------------------
    private static void printResults(String label, Long elapsedNs, long rounds) {
        float fElapsedNs = elapsedNs.floatValue();
        float elapsedMs = fElapsedNs / 1_000_000F;
        String perTime = getOpsPerTime(fElapsedNs, elapsedMs, rounds);
        String timePer = getTimePerOps(fElapsedNs, elapsedMs, rounds);
        System.out.printf(RESULTS, label, format3(elapsedMs), format(rounds), perTime, timePer);
    }

    private static String getOpsPerTime(float elapsedNs, float elapsedMs, long rounds) {
        float nsPer = rounds / elapsedNs;
        float msPer = rounds / elapsedMs;
        return nsPer < 1F ? format3(msPer) + " r/ms" : format(nsPer) + " r/ns";
    }

    private static String getTimePerOps(float elapsedNs, float elapsedMs, long rounds) {
        float nsPer = elapsedNs/ rounds;
        float msPer = elapsedMs / rounds;
        return nsPer < 1F ? format3(msPer) + " ms/r" : format(nsPer) + " ns/r";
    }

    public static String format(Number s) {
        return NumberFormat.getNumberInstance(Locale.getDefault()).format(s);
    }

    public static String format3(Number n) {
        if (n.longValue() >= 1_000_000_000) {
            return humanBytes(n.doubleValue());
        }
        String f = format(n);
        int at = f.indexOf('.');
        if (at == -1) {
            return f;
        }
        if (at == 0) {
            return f + "." + ZEROS.substring(0, 3);
        }
        return (f + ZEROS).substring(0, at + 3 + 1);
    }

    public static String humanBytes(double bytes) {
        if (bytes < HUMAN_BYTES_BASE) {
            return String.format("%.2f b", bytes);
        }
        int exp = (int) (Math.log(bytes) / Math.log(HUMAN_BYTES_BASE));
        try {
            return String.format("%.2f %s", bytes / Math.pow(HUMAN_BYTES_BASE, exp), HUMAN_BYTES_UNITS[exp]);
        }
        catch (Exception e) {
            return String.format("%.2f b", bytes);
        }
    }

    private static final String ZEROS = "000000000";
    private static final long HUMAN_BYTES_BASE = 1024;
    private static final String[] HUMAN_BYTES_UNITS = new String[] {"b", "kb", "mb", "gb", "tb", "pb", "eb"};
}
