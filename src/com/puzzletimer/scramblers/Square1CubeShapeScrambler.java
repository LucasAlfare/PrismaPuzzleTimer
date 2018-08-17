package com.puzzletimer.scramblers;

import java.util.Random;

import com.puzzletimer.models.Scramble;
import com.puzzletimer.models.ScramblerInfo;
import com.puzzletimer.solvers.Square1Solver;
import lucas.main.FullCube;
import lucas.main.NovosShapes;
import lucas.main.Search;

public class Square1CubeShapeScrambler implements Scrambler {
    private ScramblerInfo scramblerInfo;
    private Square1Solver solver;
    private Random random;

    public Square1CubeShapeScrambler(ScramblerInfo scramblerInfo) {
        this.scramblerInfo = scramblerInfo;
        this.solver = new Square1Solver();
        this.random = new Random();
    }

    @Override
    public ScramblerInfo getScramblerInfo() {
        return this.scramblerInfo;
    }

    @Override
    public Scramble getNextScramble() {
        /*
        return new Scramble(
            getScramblerInfo().getScramblerId(),
            this.solver.generate(
                this.solver.getRandomState(Square1Solver.State.id, this.random)));
         */

        return new Scramble(
                getScramblerInfo().getScramblerId(),
                new Search().solutionAsArray(FullCube.randomCube(
                        this.random, NovosShapes.SQUARE_SQUARE.randomIndex())));
    }

    @Override
    public String toString() {
        return getScramblerInfo().getDescription();
    }
}
