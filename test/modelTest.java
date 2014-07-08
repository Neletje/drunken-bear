
import exceptions.GameBezigException;
import inhoud.Kaartje;
import inhoud.Model;
import inhoud.Ronde;
import inhoud.RondePunten;
import inhoud.Speler;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Ellen
 */
public class modelTest {

    private static Speler[] spelers;
    private final Model model = Model.getInstance();

    public modelTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        spelers = new Speler[]{new Speler("A"), new Speler("B"), new Speler("C"), new Speler("D")};
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        Model.getInstance().newGame();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test1() throws GameBezigException {
        model.addSpeler(spelers[0]);
        assertEquals(model.getKaartjes().size(), 3);
        assertEquals(model.getSpelers().size(), 1);
        assertEquals(model.getPunten().size(), 1);
        model.addSpeler(spelers[1]);
        assertEquals(model.getKaartjes().size(), 3 * 2);
        assertEquals(model.getSpelers().size(), 1 * 2);
        assertEquals(model.getPunten().size(), 1);
        model.addSpeler(spelers[2]);
        assertEquals(model.getKaartjes().size(), 3 * 3);
        assertEquals(model.getSpelers().size(), 1 * 3);
        assertEquals(model.getPunten().size(), 2);
        model.addSpeler(spelers[3]);
        assertEquals(model.getKaartjes().size(), 3 * 4);
        assertEquals(model.getSpelers().size(), 1 * 4);
        assertEquals(model.getPunten().size(), 2);

        assertEquals(model.getPartner(spelers[0]), spelers[2]);
        assertEquals(model.getPartner(spelers[2]), spelers[0]);
        assertEquals(model.getPartner(spelers[1]), spelers[3]);
        assertEquals(model.getPartner(spelers[3]), spelers[1]);

        model.print();

    }

    @Test
    public void testPunten() throws GameBezigException {
        for (Speler s : spelers) {
            model.addSpeler(s);
        }

        RondePunten punten = model.getPunten(spelers[0]);
        punten.addPunten(Ronde.EEN, 3);
        punten = model.getPunten(spelers[2]);
        punten.addPunten(Ronde.EEN, 6);
        assertEquals(punten.getPuntenRonde(Ronde.EEN), 9);

        punten = model.getPunten(spelers[1]);
        punten.addPunten(Ronde.TWEE, 3);
        punten = model.getPunten(spelers[3]);
        punten.addPunten(Ronde.DRIE, 6);
        assertEquals(punten.getPuntenRonde(Ronde.UITSLAG), 9);

        model.print();
    }

    @Test
    public void testKaartjes() throws GameBezigException {
        for (Speler s : spelers) {
            model.addSpeler(s);
        }
        Kaartje[] k = model.getKaartjes(spelers[2]);
        k[0].setWoord("een");
        k[1].setWoord("twee");
        k[2].setWoord("drie");

        List<Kaartje> m = model.getKaartjes();
        assertEquals(m.get(2*3).getWoord(), "een");
        assertEquals(m.get(2*3+1).getWoord(), "twee");
        assertEquals(m.get(2*3+2).getWoord(), "drie");
        
        model.print();

    }

}
