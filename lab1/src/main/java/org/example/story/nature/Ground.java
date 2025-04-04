package org.example.story.nature;

import lombok.Getter;
import lombok.Setter;
import org.example.story.interfaces.Biome;
import org.example.story.nature.Landscape;

import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class Ground {

    private String name;
    private Set<Landscape> landscapes = new TreeSet<>((s1, s2) -> Integer.compare(s2.getAttraction(), s1.getAttraction()));
    private Biome similarBiome;

    public Ground(String name) {
        this.name = "Земля " + name;
    }

    public Ground() {
        this.name = "Земля";
    }

        public void attracts(int attractThreshold) {
        if (landscapes.isEmpty() || landscapes.iterator().next().getAttraction() < attractThreshold) {
            System.out.println(getName() + " не привлекала никакими пейзажами.");
            return;
        }
        System.out.print(getName() + " привлекала следующими пейзажами: ");

        int i = 0;
        for (Landscape landscape : landscapes) {
            if (landscape.getAttraction() < attractThreshold)
                break;
            System.out.print(landscape + ", ");
            i++;
        }
        if (i < landscapes.size())
            System.out.println(". Остальной пейзаж был менее интересным");
        else
            System.out.println();
    }

    public void isSimilarTo(boolean describeBiome) {
        System.out.println(getName() + " похожа на " + similarBiome);
        if (describeBiome)
            similarBiome.describeFlora();
    }
}
