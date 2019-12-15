import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class GenotypeTest {
    Genotype g1;
    Genotype g2;
    Genotype g3;

    @Before
    public void prepare(){
        g1 = new Genotype();
        g2 = new Genotype();
        g3 = new Genotype();
    }

    @Test
    public void createTest(){
        assertTrue(g1.verify());
        assertTrue(g2.verify());
        assertTrue(g3.verify());
        Integer[] asdf = new Integer[Genotype.GENOTYPE_SIZE];
        Arrays.fill(asdf, 1);
        Genotype badGeno = new Genotype(asdf);
        assertFalse(badGeno.verify());
        badGeno.fix();
        assertTrue(badGeno.verify());
    }

    @Test
    public void merge(){
        int asdf = 300;
        while(asdf-- > 0){
            Genotype random = new Genotype();
            assertTrue(random.merge(g2).verify());
            assertTrue(random.merge(g1).verify());
            assertTrue(random.merge(g3).verify());
        }
    }
}