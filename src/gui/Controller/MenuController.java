package gui.Controller;

import gui.Model;
import gui.View.MenuPanel;
import gui.View.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController {
    final Model model;
    final View view;
    final MenuPanel menuPanel;
    final DashboardController dashboardController;

    /**
     * Konstruktor a model és a Menu-t összekötő Controllerhez
     * @param model az adatokat és funkciókat tartalmazó model
     * @param view a megjelenítést tartalmaző view
     */
    public MenuController(Model model, View view, DashboardController dashboardController) {
        this.model = model;
        this.view = view;
        this.dashboardController = dashboardController;
        this.menuPanel = view.getMenuPanel();

        /* Menu */
        menuPanel.getSaveMenu().addActionListener(new MenuSaveListener());
        menuPanel.getLoadMenu().addActionListener(new MenuLoadListener());
        menuPanel.getResetMenu().addActionListener(new MenuResetListener());
        menuPanel.getResetOn3InvalidLogin().addActionListener(new MenuResetAfter3AttempsListener());
    }


    /**
     * Menu: Save data to files listener
     */
    class MenuSaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model.saveData();
                JOptionPane.showMessageDialog(view, "Sikeresen elmentve", "Oh yess", JOptionPane.PLAIN_MESSAGE);
                view.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Menu: Load data to files listener
     */
    class MenuLoadListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                model.getMainFolder().makeEmpty();
                model.loadData(model.getMasterPassword().getValue());
                view.getDashboradPage().removeRightPanel();
                view.getDashboradPage().showFolderListItem(model.getMainFolder());
                dashboardController.resetFolderItemSelectListener();
                dashboardController.resetFolderItemSelectListener();
                view.getDashboradPage().showTempOpenFileText();
                view.setVisible(true);
                JOptionPane.showMessageDialog(view, "Sikeresen betöltve", "Oh yess", JOptionPane.PLAIN_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Menu: Factory reset
     */
    class MenuResetListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int biztos = JOptionPane.showConfirmDialog(view, "Biztos törölsz mindent?", "Vigyázz", JOptionPane.YES_NO_OPTION);
            if (biztos == JOptionPane.YES_OPTION) {
                try {
                    model.factoryReset();
                    view.removeAll();
                    view.setVisible(false);
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Oh nooo", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * Menu: Factory reset after 3 attemps
     */
    class MenuResetAfter3AttempsListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isReset = menuPanel.getResetOn3InvalidLogin().isSelected();
            model.getSettings().setFactoryReset(isReset);
        }
    }

}
