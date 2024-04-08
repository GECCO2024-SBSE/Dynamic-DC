/*
 * Copyright (C) 2010-2018 Gordon Fraser, Andrea Arcuri and EvoSuite
 * contributors
 *
 * This file is part of EvoSuite.
 *
 * EvoSuite is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * EvoSuite is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with EvoSuite. If not, see <http://www.gnu.org/licenses/>.
 */
package org.evosuite.ga.operators.crossover;

import org.evosuite.ga.Chromosome;
import org.evosuite.ga.ConstructionFailedException;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.TestCase;
import java.util.Random;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.utils.Randomness;
import java.io.BufferedReader;
import java.io.StringReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.evosuite.testcase.statements.numeric.IntPrimitiveStatement;


/**
 * Select one random point in each individual and cross over (TPX)
 *
 * @author Gordon Fraser
 */
public class SinglePointCrossOver<T extends Chromosome<T>> extends CrossOverFunction<T>  {

    private static final long serialVersionUID = 2881387570766261795L;
    /**
     * {@inheritDoc}
     * <p>
     * A different splitting point is selected for each individual
     *
     * @param parent1
     * @param parent2
     */
    @Override
    public void crossOver(T parent1, T parent2)
            throws ConstructionFailedException {
        // Random một giá trị i
        Random random = new Random();

        int size1 = 0; // Reset size1 at the beginning
        int size2 = 0; // Reset size2 at the beginning
        int point = 0;
        double deta =0.4;
        TestSuiteChromosome t1 = (TestSuiteChromosome) parent1.clone();
        TestSuiteChromosome t2 = (TestSuiteChromosome) parent2.clone();
        TestSuiteChromosome test1 = (TestSuiteChromosome) parent1.clone();
            // Tạo một ArrayList để lưu từng dòng và số thứ tự của dòng
            for (int j=0;j<Math.min(t1.getTests().size(), t2.getTests().size())*deta;j++){
                ArrayList<String[]> linesWithIndex = readLinesWithIndex(t1.getTests().get(j).toString());

                //In ra mảng 2 chiều gồm số thứ tự và nội dung của dòng
                for (String[] lineWithIndex : linesWithIndex) {
                    lineWithIndex[3] = checkAndPrint(lineWithIndex[1]);
                    lineWithIndex[2] = lineWithIndex[1];
                    lineWithIndex[1] = String.valueOf(findMissingInteger(lineWithIndex[3]));
                    lineWithIndex[3] = lineWithIndex[3].replace("|" + lineWithIndex[1] + ";", "");
                    System.out.println("3.Stt: " + Integer.valueOf(lineWithIndex[0]) + " giá trị " + lineWithIndex[1] + " Lệnh gốc " + lineWithIndex[2] + " chữ kí " + lineWithIndex[3]);
                }
                // Tạo một ArrayList để lưu từng dòng và số thứ tự của dòng
                ArrayList<String[]> linesWithIndex2 = readLinesWithIndex(t2.getTests().get(j).toString());
                //In ra mảng 2 chiều gồm số thứ tự và nội dung của dòng
                for (String[] lineWithIndex2 : linesWithIndex2) {
                    lineWithIndex2[3] = checkAndPrint(lineWithIndex2[1]);
                    lineWithIndex2[2] = lineWithIndex2[1];
                    lineWithIndex2[1] = String.valueOf(findMissingInteger(lineWithIndex2[3]));
                    lineWithIndex2[3] = lineWithIndex2[3].replace("|" + lineWithIndex2[1] + ";", "");
                }
                TestCase t3 =t1.getTests().get(j).clone();
                size2 = linesWithIndex2.size();
                size1 = linesWithIndex.size();
                point = random.nextInt(Math.min(size1, size2)) + 1;
            for (String[] lineWithIndex : linesWithIndex) {
                if (Integer.valueOf(lineWithIndex[0]) < point || lineWithIndex[1].equals("-999999")) {
                    int index = Integer.parseInt(lineWithIndex[0]);
                    t3.addStatement(t1.getTests().get(j).getStatement(index));
//                    System.out.println(Integer.valueOf(lineWithIndex[0])+"và"+i);
                } else {
                    for (String[] lineWithIndex2 : linesWithIndex2) {
                        if (lineWithIndex[3].equals(lineWithIndex2[3])) {
                            //System.out.println("1.Stt: " + Integer.valueOf(lineWithIndex[0]) + " giá trị " + lineWithIndex[1] + " Lệnh gốc " + lineWithIndex[2] + " chữ kí " + lineWithIndex[3]);
                            int result = (int) calculateValues(Integer.valueOf(lineWithIndex[1]), Integer.valueOf(lineWithIndex2[1]));
                            lineWithIndex[1] = String.valueOf(result);
                            t3.addStatement(new IntPrimitiveStatement(t3));
                            //System.out.println("2.Stt: " + Integer.valueOf(lineWithIndex2[0]) + " giá trị " + lineWithIndex2[1] + " Lệnh gốc " + lineWithIndex2[2] + " chữ kí " + lineWithIndex2[3]);
                            //System.out.println("3.Stt: " + Integer.valueOf(lineWithIndex[0]) + " giá trị " + lineWithIndex[1] + " Lệnh gốc " + lineWithIndex[2] + " chữ kí " + lineWithIndex[3]);}
                        }
                    }
                }
            }
                double tg2 =t1.getFitness();
                t1.addTest(t3);
                if(t1.getFitness()>tg2){t1.deleteTest(t3);}

            }
        for (int j=0;j<Math.min(test1.getTests().size(), t2.getTests().size())*deta;j++){
            ArrayList<String[]> linesWithIndex = readLinesWithIndex(test1.getTests().get(j).toString());
            size1 = linesWithIndex.size();
            //In ra mảng 2 chiều gồm số thứ tự và nội dung của dòng
            for (String[] lineWithIndex : linesWithIndex) {
                lineWithIndex[3] = checkAndPrint(lineWithIndex[1]);
                lineWithIndex[2] = lineWithIndex[1];
                lineWithIndex[1] = String.valueOf(findMissingInteger(lineWithIndex[3]));
                lineWithIndex[3] = lineWithIndex[3].replace("|" + lineWithIndex[1] + ";", "");
                //System.out.println("3.Stt: " + Integer.valueOf(lineWithIndex[0]) + " giá trị " + lineWithIndex[1] + " Lệnh gốc " + lineWithIndex[2] + " chữ kí " + lineWithIndex[3]);
            }
            // Tạo một ArrayList để lưu từng dòng và số thứ tự của dòng
            ArrayList<String[]> linesWithIndex2 = readLinesWithIndex(t2.getTests().get(j).toString());
            size2 = linesWithIndex2.size();
            //In ra mảng 2 chiều gồm số thứ tự và nội dung của dòng
            for (String[] lineWithIndex2 : linesWithIndex2) {
                lineWithIndex2[3] = checkAndPrint(lineWithIndex2[1]);
                lineWithIndex2[2] = lineWithIndex2[1];
                lineWithIndex2[1] = String.valueOf(findMissingInteger(lineWithIndex2[3]));
                lineWithIndex2[3] = lineWithIndex2[3].replace("|" + lineWithIndex2[1] + ";", "");
            }
            // Random một giá trị i
        TestCase t4 = new DefaultTestCase();
        t4 =t2.getTests().get(j).clone();
        for (String[] lineWithIndex2 : linesWithIndex2) {
            if (Integer.valueOf(lineWithIndex2[0]) >= point || lineWithIndex2[1].equals("-999999")) {
                int index = Integer.parseInt(lineWithIndex2[0]);
                t4.addStatement(t2.getTests().get(j).getStatement(index));
//                    System.out.println(Integer.valueOf(lineWithIndex[0])+"và"+i);
            } else {
                for (String[] lineWithIndex : linesWithIndex) {
                    if (lineWithIndex2[3].equals(lineWithIndex[3])) {
                        //System.out.println("1.Stt: " + Integer.valueOf(lineWithIndex[0]) + " giá trị " + lineWithIndex[1] + " Lệnh gốc " + lineWithIndex[2] + " chữ kí " + lineWithIndex[3]);
                        int result = (int) calculateValues(Integer.valueOf(lineWithIndex2[1]), Integer.valueOf(lineWithIndex[1]));
                        lineWithIndex2[1] = String.valueOf(result);
                        t4.addStatement(new IntPrimitiveStatement(t4));
                    }
                }
            }
        }
            double tg =t2.getFitness();
            t2.addTest(t4);
            if(t2.getFitness()>tg){t2.deleteTest(t4);}
        }
        if (parent1.size() < 2 || parent2.size() < 2) {
            return;
        }
        if (parent1.size() < 2 || parent2.size() < 2) {
            return;
        }


        // Choose a position in the middle
        float splitPoint = Randomness.nextFloat();

        int pos1 = ((int) Math.floor((t1.size() - 1) * splitPoint)) + 1;
        int pos2 = ((int) Math.floor((t2.size() - 1) * splitPoint)) + 1;

        parent1.crossOver((T) t2, pos1, pos2);
        parent2.crossOver((T) t1, pos2, pos1);


    }
    public static ArrayList readLinesWithIndex(String inputString) {
        List<String[]> linesWithIndex = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new StringReader(inputString))) {
            String line;
            String lenh = null;
            String giatri = null;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                String[] lineWithIndex = {String.valueOf(lineNumber), line, lenh ,giatri};
                linesWithIndex.add(lineWithIndex);
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return (ArrayList) linesWithIndex;
    }

    public static String checkAndPrint(String line) {
        line = line.trim();

        if (!line.isEmpty()) {
            int firstSpaceIndex = line.indexOf(' ');
            if (line.contains(".")) {
                // Đây là Method Call
                return "Method|"+line;
            } else {

                // Đây là Declaration
                String type = line.substring(0, firstSpaceIndex);
                line = line.substring(firstSpaceIndex + 1).trim();

                int secondSpaceIndex = line.indexOf(' ');
                if (secondSpaceIndex != -1) {
                    String name = line.substring(0, secondSpaceIndex);
                    line = line.substring(secondSpaceIndex + 1).trim();

                    // Lấy giá trị từ phần còn lại của dòng
                    String value = line.replace("("," ");
                    value = value.replace(")","");
                    value = value.replace("=","");


                    // In ra thông tin Declaration
                    return "Declaration|"+ type + "|" + name + "|" + value.trim();
                } else {
                    // Đây là Method Call
                    return "Method|"+line;
                }
            }
        }
        // Trường hợp mặc định khi không thỏa mãn điều kiện nào
        return "Invalid Input";
    }
    public static int findMissingInteger(String input) {
        String[] parts = input.split("\\|"); // Tách xâu bằng dấu "|"
        // Lấy phần tử cuối cùng trong mảng parts
        String lastPart = parts[parts.length - 1];
        try {
            int integer = Integer.valueOf(lastPart.replace(";",""));
            return integer;

        } catch (NumberFormatException e) {
            return -999999; // Trả về giá trị mặc định nếu có lỗi
        }

    }
    public static double calculateValues(int v1, int v2) {
        double u = 2.5;
        double β;
        boolean b = new Random().nextBoolean();
        double ηc = Math.random();
        if (u < 0.5) {
            β = 2 * Math.pow(u, 1.0 / (ηc + 1.0));
        } else if (u == 0.5) {
            β = 1;
        } else {
            β = Math.pow(0.5/(1.0-u), 1.0 / (ηc + 1.0));
        }

        double v;
        if (b) {
            v = ((v1 - v2) * 0.5) - (β * 0.5 * Math.abs(v1 - v2)) / (ηc + 1);
        } else {
            v = ((v1 - v2) * 0.5) + (β * 0.5 * Math.abs(v1 - v2)) / (ηc + 1);
        }

        return v;
    }


}


