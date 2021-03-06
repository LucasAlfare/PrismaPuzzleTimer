package com.puzzletimer.tips;

import com.puzzletimer.models.Scramble;
import com.puzzletimer.solvers.Square1ShapeSolver;
import com.puzzletimer.util.StringUtils;

import static com.puzzletimer.Internationalization.identifier;

public class Square1OptimalCubeShapeTip implements Tip {
    @Override
    public String getTipId() {
        return "SQUARE-1-OPTIMAL-CUBE-SHAPE";
    }

    @Override
    public String getPuzzleId() {
        return "SQUARE-1";
    }

    @Override
    public String getTipDescription() {
        return identifier("tip.SQUARE-1-OPTIMAL-CUBE-SHAPE");
    }

    @Override
    public String getTip(Scramble scramble) {
        String[] solution = Square1ShapeSolver.solve(
                Square1ShapeSolver.State.id.applySequence(scramble.getSequence()));

        return identifier("tip.SQUARE-1-OPTIMAL-CUBE-SHAPE") + ":\n  " + StringUtils.join(" ", solution);
    }

    @Override
    public String toString() {
        return getTipDescription();
    }
}
