package app.control;

import app.control.interfaces.CRUDSymposiumInterface;
import java.util.ArrayList;
import java.util.List;

public class CRUDSymposiumTest implements CRUDSymposiumInterface{
    private List<String> speakers;
    private String overview;
    private String place;
    private String title;
    private int vacancies = 2;
    
    public CRUDSymposiumTest(String[] id) {
        speakers = new ArrayList<>();
        title = id[0];
        overview = "A abelha operária (ou obreira), preocupada com sua própria sobrevivência e encarregada da proteção da colmeia como um todo, "
                + "tem um ferrão na parte traseira para ataque em situações de suposto perigo. Esse ferrão tem pequenas farpas, o que impede que "
                + "seja retirado com facilidade da pele humana.\n"
                + "Quando uma abelha se sente ameaçada, ela utiliza o ferrão no animal que estiver por perto. Depois de dar a ferroada, ela tenta escapar e, por "
                + "causa das farpas, a parte posterior do abdômen onde se localiza o ferrão na maioria das vezes fica presa na pele do animal e, "
                + "em alguns casos, a abelha perde uma parte do intestino, morrendo logo em seguida. Já ao picar insetos, a abelha muitas vezes "
                + "consegue retirar as farpas da vítima e ainda sobreviver.\n"
                + "Os órgãos prejudicados das abelhas em caso de o ferrão ficar preso na vitima e levar órgãos juntos variam de intestino até o coração.\n"
                + "A ferroada da abelha no ser humano é muito dolorosa, e a sensação instantânea é semelhante a de levar um choque de alta voltagem. "
                + "Seu ferrão é unido a um sistema venenoso que faz com que a pele da vítima inche levemente na região (cerca de 2 cm ao redor), podendo "
                + "ficar avermelhada, dolorida e coçando por até dois dias.\n";
        System.out.println(overview.length());
        place = "Bloco A, sala 317\n"
                + "Capacidade máxima: 30\n"
                + "Inscrito: 25\n"
                + "\nHora inicio: 13:30\n"
                + "Hora fim: 15:30";
        
        speakers.add("Cortella");
        speakers.add("Bill Gates");
        speakers.add("Paulo Henrique");
        speakers.add("Vinicius");
        speakers.add("Topster");
        speakers.add("Topper");
        speakers.add("Topson");
        speakers.add("Yoda");
        speakers.add("Lula");
        speakers.add("Haddad");
        
    }
    
    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public List<String> getSpeakers() {
        return speakers;
    }

    @Override
    public String getPlace() {
        return place;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getSpeakersVacancies() {
        return vacancies;
    }
    
}
