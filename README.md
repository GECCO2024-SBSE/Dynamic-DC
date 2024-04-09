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
# Citation
Thi-Mai-Anh Bui, Van-Tri Do, Minh Vu Le, Quoc-Trung Bui. 2024. Dy-
namic Difficulty Coefficient in Search-Based Software Testing: Targeting to
Hard Branch Coverage. In Genetic and Evolutionary Computation Conference
(GECCO ’24 Companion), July 14–18, 2024, Melbourne, VIC, Australia. ACM,
New York, NY, USA, 4 pages. https://doi.org/10.1145/3638530.3654411









