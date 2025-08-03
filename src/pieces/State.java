/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package pieces;

/**
 *
 * @author mogli
 */
public enum State {
    NON_STARTED("não iniciado"),
    INCOMPLETE("incompleto"),
    COMPLETE("completo");

    private final String label;

    State(final String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
