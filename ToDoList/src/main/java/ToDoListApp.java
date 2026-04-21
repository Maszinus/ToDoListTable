import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ToDoListApp extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> itemsList;
    private JTextField itemInput;
    private JButton addButton;
    private JButton deleteButton;
    private JLabel counterLabel;

    public ToDoListApp() {
        setTitle("Menedżer Zadań (To-Do List)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(400, 500));

        // NORTH: Input panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        itemInput = new JTextField(20);
        itemInput.setName("itemInput");
        addButton = new JButton("Dodaj");
        addButton.setName("addButton");
        inputPanel.add(itemInput);
        inputPanel.add(addButton);
        add(inputPanel, BorderLayout.NORTH);

        // CENTER: List
        listModel = new DefaultListModel<>();
        itemsList = new JList<>(listModel);
        itemsList.setName("itemsList");
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.setPrototypeCellValue("Sample task text long enough            ");
        JScrollPane scrollPane = new JScrollPane(itemsList);
        scrollPane.setPreferredSize(new Dimension(380, 350));
        add(scrollPane, BorderLayout.CENTER);

        // SOUTH: Delete + Counter
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        deleteButton = new JButton("Usuń zaznaczone");
        deleteButton.setName("deleteButton");
        counterLabel = new JLabel("Zadań: 0");
        southPanel.add(deleteButton);
        southPanel.add(counterLabel);
        add(southPanel, BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteSelectedTask());
        itemInput.addActionListener(e -> addTask()); // Enter key

        // Double-click delete
        itemsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    deleteSelectedTask();
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
        updateCounter();
    }

    private void addTask() {
        String text = itemInput.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pole nie może być puste!", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }
        listModel.addElement(text);
        itemInput.setText("");
        itemsList.setSelectedIndex(listModel.getSize() - 1);
        updateCounter();
        itemsList.ensureIndexIsVisible(itemsList.getSelectedIndex());
    }

    private void deleteSelectedTask() {
        int idx = itemsList.getSelectedIndex();
        if (idx >= 0) {
            listModel.removeElementAt(idx);
            updateCounter();
        } else {
            JOptionPane.showMessageDialog(this, "Zaznacz zadanie do usunięcia!", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateCounter() {
        counterLabel.setText("Zadań: " + listModel.getSize());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ToDoListApp().setVisible(true);
        });
    }
}
