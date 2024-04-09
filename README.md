# Dynamic Difficulty Coefficient in Search-Based Software Testing:
  Targeting to Hard Branch Coverage
# Overview 
Within the domain of Search-Based Software Testing (SBST), there
is a growing research emphasis on hard branch coverage during
the generation of test data. A considerable portion of this research
strives to enhance the fitness function by integrating branch complexity, providing additional insights to finetune the search mechanism. However, current studies encounter a limitation as the computation of branch difficulty is straightforward, potentially leading
to inefficiency and getting stuck in local optima, especially when
dealing with nested predicates. This paper introduces a genetic algorithm designed to tackle test data generation with a specific focus
on hard branches. The proposed algorithm aims to identify optimal
testsuite candidates, covering as many branches as possible by
employing a fitness function dynamically computed based on the
difficulty coefficient of branch targets. Additionally, we enhance
genetic operators to guide the evolutionary process toward hard
branches. Empirical studies indicate the efficiency and effectiveness
of the proposed approach, significantly outperforming counterparts,
especially for covering difficult-to-reach branches.
# Dataset
  **Table 1: Benchmark Dataset Statistics**


![image](https://github.com/LeMINhVux/GECCO_2024_Constraint_Based_Software_Test_Generation/assets/91714533/8b876e49-640a-41e9-ad26-6bde91c9acf5)

#  Results
  **Table 2: The Coverage Rate of three approaches**

![image](https://github.com/LeMINhVux/GECCO_2024_Constraint_Based_Software_Test_Generation/assets/91714533/48662006-ebf8-4490-b969-eacba16f6449)

Table 2 presents a comparison of the coverage rates for 14 pro-grams using three different approaches.
# Execute
[README EVOSUITE](https://github.com/LeMINhVux/GECCO_2024_Constraint_Based_Software_Test_Generation/blob/main/Program/README.md)
# References
[1] Arthur Baars, Mark Harman, Youssef Hassoun, Kiran Lakhotia, Phil McMinn,
Paolo Tonella, and Tanja Vos. 2011. Symbolic search-based testing. In 2011 26th
IEEE/ACM International Conference on Automated Software Engineering (ASE 2011).
IEEE, 53–62.

[2] Thi-Mai-Anh Bui, Quoc-Trung Bui, and Van-Tri Do. 2023. A Novel Fitness Function for Automated Software Test Case Generation Based on Nested Constraint
Hardness. In Proceedings of the Companion Conference on Genetic and Evolutionary
Computation. 791–794.

[3] Gordon Fraser and Andrea Arcuri. 2011. Evosuite: automatic test suite generation
for object-oriented software. In Proceedings of the 19th ACM SIGSOFT symposium
and the 13th European conference on Foundations of software engineering. 416–419.

[4] Jan Malburg and Gordon Fraser. 2014. Search-based testing using constraint-based
mutation. Software Testing, Verification and Reliability 24, 6 (2014), 472–495.

[5] Phil McMinn. 2004. Search-based software test data generation: a survey. Software
testing, Verification and reliability 14, 2 (2004), 105–156.

[6] Annibale Panichella, Fitsum Meshesha Kifetew, and Paolo Tonella. 2017. Automated test case generation as a many-objective optimisation problem with
dynamic selection of the targets. IEEE Transactions on Software Engineering 44, 2
(2017), 122–158

[7] Abdelilah Sakti, Yann-Gaël Guéhéneuc, and Gilles Pesant. 2013. Constraint-based
fitness function for search-based software testing. In Integration of AI and OR
Techniques in Constraint Programming for Combinatorial Optimization Problems:
10th International Conference, CPAIOR 2013, Yorktown Heights, NY, USA, May
18-22, 2013. Proceedings 10. Springer, 378–385.

[8] Xiong Xu, Ziming Zhu, and Li Jiao. 2017. An adaptive fitness function based
on branch hardness for search based testing. In Proceedings of the Genetic and
Evolutionary Computation Conference. 1335–1342.

[9] Ziming Zhu and Li Jiao. 2019. Improving search-based software testing by
constraint-based genetic operators. In Proceedings of the Genetic and Evolutionary
Computation Conference. 1435–1442.

[10] Ziming Zhu, Li Jiao, and Xiong Xu. 2018. A Dynamic Fitness Function Based on
Branch Hardness for Search Based Software Testing. In 2018 25th Asia-Pacific
Software Engineering Conference (APSEC). IEEE, 159–168








