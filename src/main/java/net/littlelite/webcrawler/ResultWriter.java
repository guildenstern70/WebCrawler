/**
 * WebCrawler
 * Copyright (C) 2021 Alessio Saltarin
 * MIT License
 */

package net.littlelite.webcrawler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResultWriter {

    private static final String CSV_SEPARATOR = ";";

    public static void writeToCSV(List<Result> resultList)
    {
        System.out.println("Writing CSV...");
        try
        {
            var oFile = new File("results.csv");
            var fos = new FileOutputStream(oFile);
            var os = new OutputStreamWriter(fos, StandardCharsets.UTF_16);
            var bw = new BufferedWriter(os);

            var header = new StringBuilder();
            header.append("WEBSITE");
            header.append(CSV_SEPARATOR);
            header.append("URL");
            header.append(CSV_SEPARATOR);
            header.append("DEPTH");
            header.append(CSV_SEPARATOR);
            header.append("RESULT");
            header.append(CSV_SEPARATOR);
            bw.write(header.toString());
            bw.newLine();

            for (Result result : resultList)
            {
                var oneLine = new StringBuilder();
                oneLine.append(result.getWebSite());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(result.getUrl());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(result.getDepth());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(result.getResult());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
            System.out.println("Done. CSV File: " + oFile.getAbsolutePath());
        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }

    }

}
