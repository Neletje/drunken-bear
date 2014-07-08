/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inhoud;

import java.util.Objects;

/**
 *
 * @author Ellen
 */
public class Speler {

    private String name;

    public Speler(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Speler{" + "name=" + name + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        for (int i = 0; i < name.length(); i++) {
            hash += ((int) name.charAt(i));
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Speler other = (Speler) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

}
