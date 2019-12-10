import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Genotype {
    public static final Integer GENOTYPE_SIZE = 32;
    private Integer[] genotype;

    public Genotype() {
        this.genotype = new Integer[GENOTYPE_SIZE];
        this.randomize();
    }

    public Genotype(Integer[] genotype) {
        this.genotype = Arrays.copyOf(genotype, GENOTYPE_SIZE);
        Arrays.sort(this.genotype);
    }

    public void setArray(Integer[] genotype) {
        this.genotype = Arrays.copyOf(genotype, GENOTYPE_SIZE);
        Arrays.sort(this.genotype);
    }

    public String toString() {
        return Arrays.toString(this.genotype);
    }

    public int getRandom() {
        Random rand = new Random();
        return this.genotype[rand.nextInt(GENOTYPE_SIZE)];
    }

    public void fix() {
        Random rand = new Random();
        List<Integer> genotypeList = new ArrayList<>(Arrays.asList(this.genotype));
        while (!this.verify(genotypeList)) {
            for (int i = 0; i < 8; i++) {
                while (!genotypeList.contains(i)) {
                    genotypeList.set(rand.nextInt(GENOTYPE_SIZE), i);
                }
            }
        }
        this.genotype = genotypeList.toArray(Integer[]::new);
        Arrays.sort(this.genotype);
    }

    public boolean verify() {
        return this.verify(new ArrayList<>(Arrays.asList(this.genotype)));
    }

    private boolean verify(List<Integer> list) {
        List<Integer> genotypeList = list;
        for (int i = 0; i < 8; i++) {
            if (!genotypeList.contains(i)) {
                return false;
            }
        }
        return true;
    }

    public void randomize() {
        Random rand = new Random();
        this.genotype = Arrays.stream(this.genotype).map(v -> v = rand.nextInt(8)).toArray(Integer[]::new);
        this.fix();
        Arrays.sort(this.genotype);
    }

    // Reproduction xD
    public Genotype merge(Genotype mum) {
        Random rand = new Random();
        List<Integer> indexesList = IntStream.range(1, GENOTYPE_SIZE).boxed().collect(Collectors.toCollection(LinkedList::new));

        Genotype first = this;
        Genotype second = mum;
        if (rand.nextBoolean()) {
            first = mum;
            second = this;
        }

        Integer splitIndex1 = new Random().nextInt(GENOTYPE_SIZE);
        Integer splitIndex2;
        do {
            splitIndex2 = new Random().nextInt(GENOTYPE_SIZE);
        } while (splitIndex1 == splitIndex2);

        if (splitIndex1 > splitIndex2) {
            Integer tmp = splitIndex1;
            splitIndex1 = splitIndex2;
            splitIndex2 = tmp;
        }

        Genotype kidGenotype = new Genotype();
        for (int i = 0; i < splitIndex1; i++) {
            kidGenotype.genotype[i] = first.genotype[i];
        }
        for (int i = splitIndex1; i < splitIndex2; i++) {
            kidGenotype.genotype[i] = second.genotype[i];
        }

        for (int i = splitIndex2; i < GENOTYPE_SIZE; i++) {
            kidGenotype.genotype[i] = first.genotype[i];
        }
        return kidGenotype;

    }
}
