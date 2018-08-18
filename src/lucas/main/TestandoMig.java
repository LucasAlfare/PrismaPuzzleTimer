package lucas.main;

import java.util.ArrayList;
import java.util.Arrays;

public class TestandoMig {

    public static final ArrayList<CubeShapeIndex> SHAPES = new ArrayList<>();

    public static void main(String[] args) {

    }

    public static ArrayList<CubeShapeIndex> templateParaLista(String template) {
        ArrayList<String> separados = new ArrayList<>(
                Arrays.asList(template.replaceAll(" ", "").toUpperCase().replaceAll("X", "x").split(";"))
        );
        System.out.println(separados);
        CubeShapeIndex[] values = CubeShapeIndex.values();
        ArrayList<CubeShapeIndex> ret = new ArrayList<>();
        for (CubeShapeIndex value : values) {
            if (separados.contains(value.toString())) {
                ret.add(value);
            }
        }

        return ret;
    }

    public static String listaParaTemplate(ArrayList<CubeShapeIndex> lista) {
        StringBuilder ret = new StringBuilder();
        for (CubeShapeIndex s : lista) {
            ret.append(s).append("; ");
        }
        return ret.toString();
    }
}
