package lucas.main;//package teste_com_cstimer;

import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println(scramble());
        }
    }

    public static String scramble(){
        NovosShapes[] shapes = {
                NovosShapes.x222_PAIRED,
                NovosShapes.PAIRED_x222,
                NovosShapes.x222_PERPENDICULAR,
                NovosShapes.PERPENDICULAR_x222,
                NovosShapes.x222_PARALLEL,
                NovosShapes.PARALLEL_x222,

                NovosShapes.x411_PAIRED,
                NovosShapes.PAIRED_x411,
                NovosShapes.x411_PERPENDICULAR,
                NovosShapes.PERPENDICULAR_x411,
                NovosShapes.x411_PARALLEL,
                NovosShapes.PARALLEL_x411,

                NovosShapes.x33_PAIRED,
                NovosShapes.PAIRED_x33,
                NovosShapes.x33_PARALLEL,
                NovosShapes.PARALLEL_x33,
                NovosShapes.x33_PERPENDICULAR,
                NovosShapes.PERPENDICULAR_x33,

                NovosShapes.SCALLOP_BARREL,
                NovosShapes.BARREL_SCALLOP,

                NovosShapes.SHIELD_MUSHROOM,
                NovosShapes.MUSHROOM_SHIELD,

                NovosShapes.SCALLOP_KITE,
                NovosShapes.KITE_SCALLOP,

                NovosShapes.SHIELD_SQUARE,
                NovosShapes.SQUARE_SHIELD,

                NovosShapes.SCALLOP_SCALLOP,
                NovosShapes.SCALLOP_SCALLOP,//duplo para garantir a mesma chance de serem sorteados

                NovosShapes.SHIELD_SHIELD,
                NovosShapes.SHIELD_SHIELD,//duplo para garantir a mesma chance de serem sorteados

                NovosShapes.x8_STAR,
                NovosShapes.STAR_x8,

                NovosShapes.KITE_BARREL,
                NovosShapes.BARREL_KITE
        };

        Random r = new Random();
        return new Search().solution(FullCube.randomCube(r, shapes[r.nextInt(shapes.length)].randomIndex()));
    }
}
