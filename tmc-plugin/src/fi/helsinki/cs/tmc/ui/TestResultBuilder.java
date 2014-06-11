package fi.helsinki.cs.tmc.ui;

import fi.helsinki.cs.tmc.data.TestCaseResult;
import fi.helsinki.cs.tmc.model.SourceFileLookup;

import java.util.ArrayList;
import java.util.List;

public class TestResultBuilder {

    private TestResultBuilder() {
    }

    public static List<ResultCell> buildCells(final List<TestCaseResult> testCaseResults, final boolean showAll) {

        final SourceFileLookup sourceFileLookup = SourceFileLookup.getDefault();
        final List<ResultCell> resultCells = new ArrayList<ResultCell>();

        for (TestCaseResult result : testCaseResults) {

            if (showAll || !result.isSuccessful()) {

                resultCells.add(new TestCaseResultCell(result, sourceFileLookup).getCell());

                if(!showAll) {
                    break;
                }
            }
        }

        return resultCells;
    }
}
