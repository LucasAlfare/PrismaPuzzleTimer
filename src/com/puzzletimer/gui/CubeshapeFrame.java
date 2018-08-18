package com.puzzletimer.gui;

import com.puzzletimer.Main;
import lucas.main.CubeShapeIndex;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CubeshapeFrame extends JFrame {

    private JComboBox<String> cubeShapes;
    private JScrollPane scrollPane;
    private JList<String> adicionados;
    private JButton adicionar, remover, limpar, adicionarDaColagem, gerarColagem;

    private FrameAuxiliar frameAuxiliar;
    private static String templateColagem;

    public CubeshapeFrame(){
        setSize(300, 400);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setTitle("Assistente de Cubeshape");
        setResizable(false);
        setLayout(new MigLayout());

        frameAuxiliar = new FrameAuxiliar();

        cubeShapes = new JComboBox<>();
        cubeShapes.setModel(new DefaultComboBoxModel<>(
                nomeDosCasos()
        ));

        scrollPane = new JScrollPane();
        adicionados = new JList<>();
        adicionados.setModel(atualizado());
        scrollPane.setViewportView(adicionados);

        adicionar = new JButton("adicionar");
        adicionar.addActionListener(e -> {
            if (cubeShapes.getSelectedIndex() != -1){
                if (!Main.shapes.contains(CubeShapeIndex.values()[cubeShapes.getSelectedIndex()])){
                    Main.shapes.add(CubeShapeIndex.values()[cubeShapes.getSelectedIndex()]);
                    adicionados.setModel(atualizado());
                    System.out.println(Main.shapes);
                }
            }
        });

        remover = new JButton("remover selecionado");
        remover.addActionListener(e -> {
            int indice = adicionados.getSelectedIndex();
            if (indice != -1){
                for (CubeShapeIndex ns : CubeShapeIndex.values()){
                    if (ns.toString().equals(adicionados.getSelectedValue())){
                        Main.shapes.remove(ns);
                        adicionados.setModel(atualizado());
                        break;
                    }
                }
            }
        });

        limpar = new JButton("limpar");
        limpar.addActionListener(e -> {
            Main.shapes.clear();
            adicionados.setModel(atualizado());
        });

        adicionarDaColagem = new JButton("adicionar da colagem");
        adicionarDaColagem.addActionListener(e -> {
            frameAuxiliar = new FrameAuxiliar();
            frameAuxiliar.setVisible(!frameAuxiliar.isVisible());
            frameAuxiliar.setLocationRelativeTo(CubeshapeFrame.this);
            frameAuxiliar.iniciar(null, true);
            //TODO: adicionar shapes a partir da colagem

        });

        gerarColagem = new JButton("gerar colagem");
        gerarColagem.addActionListener(e -> {
            frameAuxiliar = new FrameAuxiliar();
            frameAuxiliar.setVisible(!frameAuxiliar.isVisible());
            frameAuxiliar.setLocationRelativeTo(CubeshapeFrame.this);
            //TODO criar geração de template
            templateColagem = listaParaTemplate(Main.shapes);
            frameAuxiliar.iniciar(templateColagem, false);
        });

        add(new JLabel("Selecione o Cubeshape:"), new CC().grow().wrap());
        add(cubeShapes, new CC().grow().wrap());
        add(scrollPane, new CC().grow().wrap());
        add(adicionar, new CC().grow().wrap());
        add(remover, new CC().grow().wrap());
        add(limpar, new CC().grow().wrap());
        add(adicionarDaColagem, new CC().grow().wrap());
        add(gerarColagem, new CC().grow().wrap());

        pack();
    }

    private String[] nomeDosCasos(){
        CubeShapeIndex[] shapes = CubeShapeIndex.values();
        String[] nomes = new String[shapes.length];
        for (int i = 0; i < shapes.length; i++) {
            nomes[i] = shapes[i].toString();
        }

        return nomes;
    }

    private AbstractListModel<String> atualizado(){
        String[] aux;
        if (Main.shapes.isEmpty()){
            aux = new String[]{"sem shapes ainda!"};
        } else {
            aux = new String[Main.shapes.size()];
            for (int i = 0; i < Main.shapes.size(); i++) {
                aux[i] = Main.shapes.get(i).toString();
            }
        }

        return new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return aux.length;
            }

            @Override
            public String getElementAt(int index) {
                return aux[index];
            }
        };
    }

    /*
    x8_star;
    x222_perpendicular;
    x222_parallel;
    x222_paired;
    x411_perpendicular;
    x411_paired;
    x411_parallel;
    x33_parallel;
    x33_paired;
    x33_perpendicular;
     */
    private static ArrayList<CubeShapeIndex> templateParaLista(String template){
        ArrayList<String> separados = new ArrayList<>(
                Arrays.asList(
                        template
                                .trim()
                                .replaceAll(" ", "")
                                .toUpperCase()
                                .replaceAll("X", "x")
                                .replaceAll("\n", "")
                                .split(";"))
        );
        CubeShapeIndex[] values = CubeShapeIndex.values();
        ArrayList<CubeShapeIndex> ret = new ArrayList<>();
        for (CubeShapeIndex value : values) {
            if (separados.contains(value.toString())) {
                ret.add(value);
            }
        }

        return ret;
    }

    private static String listaParaTemplate(ArrayList<CubeShapeIndex> lista){
        StringBuilder ret = new StringBuilder();
        for (CubeShapeIndex s : lista){
            ret.append(s).append(";\n");
        }
        return ret.toString();
    }

    private class FrameAuxiliar extends JFrame {

        private javax.swing.JTextArea area;
        private javax.swing.JLabel heading;
        private javax.swing.JScrollPane jScrollPane1;
        private javax.swing.JButton salvar;

        public FrameAuxiliar(){
            this.setSize(400, 400);
            this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setIconImage(CubeshapeFrame.this.getIconImage());
            this.setResizable(false);
            this.setLayout(new MigLayout());
            //iniciar(null, true);
        }

        public void iniciar(String conteudo, boolean adicionando){
            jScrollPane1 = new JScrollPane();
            salvar = new JButton("salvar");

            heading = new JLabel(adicionando ? "cole os nomeDosCasos aqui:" : "copie e salve sua colagem");
            heading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            heading.setText("texto");

            area = new JTextArea(adicionando ? "vazio" : conteudo);
            area.setColumns(20);
            area.setRows(5);
            jScrollPane1.setViewportView(area);

            salvar.setText("jButton1");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(heading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                                            .addComponent(salvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addContainerGap())
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(heading)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(salvar))
            );

            if (adicionando){
                salvar.addActionListener(e -> {
                    if (!area.getText().isEmpty()){
                        templateColagem = area.getText();

                        Main.shapes = null;
                        Main.shapes = templateParaLista(templateColagem);
                        System.out.println();
                        System.out.println("template: " + templateColagem);
                        System.out.println("tamanho após paste: " + Main.shapes.size());
                        adicionados.setModel(atualizado());
                        templateColagem = "";

                        FrameAuxiliar.this.setVisible(false);
                    }
                });
            } else {
                salvar.setEnabled(false);
            }

            pack();

            /*
            getContentPane().removeAll();
            JTextArea area = new JTextArea(adicionando ? "vazio" : conteudo);

            this.add(
                    new JLabel(adicionando ? "cole os nomeDosCasos aqui:" : "copie e salve sua colagem"),
                    new CC().wrap()
            );

            area.setPreferredSize(new Dimension(200, 100));
            area.setLineWrap( true );
            area.setWrapStyleWord( true );
            area.setColumns(20);
            area.setRows(5);
            JScrollPane pane = new JScrollPane(area);

            this.add(pane, new CC().span());

            if (adicionando){
                JButton salvar = new JButton("salvar");
                salvar.addActionListener(e -> {
                    if (!area.getText().isEmpty()){
                        templateColagem = area.getText();

                        Main.shapes = null;
                        Main.shapes = templateParaLista(templateColagem);
                        System.out.println();
                        System.out.println("template: " + templateColagem);
                        System.out.println("tamanho após paste: " + Main.shapes.size());
                        adicionados.setModel(atualizado());
                        templateColagem = "";

                        FrameAuxiliar.this.setVisible(false);
                    }
                });

                this.add(salvar, "align center bottom");
            }

            this.pack();
             */
        }
    }
}
