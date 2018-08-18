package com.puzzletimer.scramblers;

import com.puzzletimer.Main;
import com.puzzletimer.models.Scramble;
import com.puzzletimer.models.ScramblerInfo;
import com.puzzletimer.solvers.Square1Solver;
import lucas.main.CubeShapeIndex;
import lucas.main.FullCube;
import lucas.main.Search;

import java.util.Random;

public class Square1RandomScrambler implements Scrambler {
    private ScramblerInfo scramblerInfo;
    private Square1Solver solver;
    private Random random;

    public Square1RandomScrambler(ScramblerInfo scramblerInfo) {
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
                this.solver.getRandomState(this.random)));
         */
        CubeShapeIndex aleatorio = Main.shapes.isEmpty()
                ? null
                : Main.shapes.get(this.random.nextInt(Main.shapes.size()));

        String[] arrSeq = new Search().solutionAsArray(
                aleatorio == null
                        ? FullCube.randomCube(this.random)
                        : FullCube.randomCube(aleatorio));

        return new Scramble(
                getScramblerInfo().getScramblerId(),
                arrSeq);
    }

    @Override
    public String toString() {
        return getScramblerInfo().getDescription();
    }
}
