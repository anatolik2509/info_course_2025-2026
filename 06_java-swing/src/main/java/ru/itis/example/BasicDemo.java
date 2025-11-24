package ru.itis.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class BasicDemo {
    public static void main(String[] args) {
        // Создаём окно
        JFrame frame = new JFrame("My awesome GUI");
        // Задаём окну величину в пикселях
        frame.setSize(400, 500);
        // Это чтобы программа останавливалась, когда окно закрыто
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Мы не умеем пока делать адаптивную программу, поэтому запрещаем изменять размер окна
        frame.setResizable(false);

        // Метод, который соберёт нам кнопку
        JButton button = getJButton(frame);

        // Убираем Layout, который отвечает за расположение компонентов в контейнере
        // Разберёмся с ними потом, пока пусть не мешает
        frame.setLayout(null);
        // Добавляем в окно нашу кнопку
        frame.add(button);

        // Чтобы окно было видно. Без этого не видно(
        frame.setVisible(true);
    }

    private static JButton getJButton(JFrame frame) {
        // Создаём объект для кнопки с внутренним текстом
        JButton button = new JButton("Click me");
        // Красим кнопку в красивый и эстетичный зелёный цвет
        button.setBackground(Color.GREEN);
        // Прибиваем кнопку гвоздями к конкретному месту
        button.setBounds(new Rectangle(150, 200, 100, 100));

        // Тут мы получаем размер экрана
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Чтобы кнопка работала - мы можем добавлять к ней листенеры. Почти как в JS
        button.addActionListener(e -> {
            // JDialog - специальный компонент, если мы хотим ещё какие-нибудь маленькие окошки показать пользователю.
            // Не полноценное окно, а окошко поменьше
            // Задаём наш основной фрейм - как владелец диалога
            JDialog dialog = new JDialog(frame);
            dialog.setSize(new Dimension(100, 100));
            // Добавляем текстовое поле
            dialog.add(new JTextField("Вы нажали кнопку"));
            dialog.setVisible(true);
            setRandomScreenLocation(dialog, screenSize);
        });
        return button;
    }

    // Метод для размещения компонента в рандомном месте экрана.
    // Кстати, Component - вершина в иерархии классов swing и awt. Туда можно что угодно передать (но результат будет не всегда предсказуем)
    private static void setRandomScreenLocation(Component component, Dimension screenSize) {
        Random random = new Random();
        int x = random.nextInt(screenSize.width - component.getWidth());
        int y = random.nextInt(screenSize.height - component.getHeight());
        component.setLocation(x, y);
    }
}
