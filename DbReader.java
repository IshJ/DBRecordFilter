import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DbReader {
    static final String logPath = "/home/ishadi/Desktop/log3.out";
    static final String dbHome = "/home/ishadi/Documents/AndroidCFI/apps/weather/v1/db/";
    static final String MainAppDbPath = dbHome + "ground_truth_full.out";
    static final String sideChannelDbPath = dbHome + "side_channel_info_full.out";
    static final String MainAppProcessedPath = dbHome + "ground_truth.out";
    static final String sideChannelProcessedPath = dbHome + "side_channel_info.out";
    static final String sideChannelProcessedPath1 = dbHome + "side_channel_info_1.out";
    static final String sideChannelProcessedPath2 = dbHome + "side_channel_info_2.out";
    static final String sideChannelProcessedPath3 = dbHome + "side_channel_info_3.out";
    static final String sideChannelProcessedPath4 = dbHome + "side_channel_info_4.out";
    static final String sideChannelProcessedPath5 = dbHome + "side_channel_info_5.out";
    static final String stripString = "\\|";
    static final String filteredOutputPath = dbHome + "filtered.out";

    static int filterThreshold = 300;

    public static void main(String[] args) throws IOException {

        filterThreshold = Integer.parseInt(args[0]);

        List<String> logLines = Files.lines(Paths.get(logPath), StandardCharsets.ISO_8859_1).collect(Collectors.toList());
//            List<String> groundTruthLines = Files.lines(Paths.get(MainAppProcessedPath), StandardCharsets.ISO_8859_1).collect(Collectors.toList());
        List<String> sideChannelLines = Files.lines(Paths.get(sideChannelDbPath), StandardCharsets.ISO_8859_1).collect(Collectors.toList());
        List<String> groundTruthLines = Files.lines(Paths.get(MainAppProcessedPath), StandardCharsets.ISO_8859_1).collect(Collectors.toList());
//            List<String> sideChannelLines = Files.lines(Paths.get(MainAppDbPath), StandardCharsets.ISO_8859_1).collect(Collectors.toList());


        getSideChannelRecordsUnderTime(filterThreshold, sideChannelLines);
    }

    private static void getSideChannelRecordsUnderTime(Integer time, List<String> lines) throws IOException {
        List<String> filteredLines = new ArrayList<>();
        lines.stream().filter(line->Integer.parseInt(line.split(stripString)[1]) < time + 1).forEach(filteredLines::add);
        System.out.println("Number of records: "+filteredLines.size());
        Files.write(Path.of(filteredOutputPath), filteredLines, StandardCharsets.ISO_8859_1);

    }
}
