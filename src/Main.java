import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Text text1 = new Text("Hello, World!");
        Text text2 = new Text("Java Programming");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addElement(text1);
        verticalLayout.addElement(text2);

        Frame frame = new Frame("Example", verticalLayout);

        frame.calculateSize();
        frame.render();
    }
}

interface UiElement {
    void calculateSize();

    void render();

    int getWidth(); // Метод для получения ширины

    int getHeight(); // Метод для получения высоты
}

class Text implements UiElement {
    private final String text;
    private int width;

    public Text(String text) {
        this.text = text;
    }

    @Override
    public void calculateSize() {
        this.width = text.length(); // ширина - длина текста
    }

    @Override
    public void render() {
        System.out.println(text);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 1;
    }

}

class Frame implements UiElement {
    private final String title;
    private final UiElement innerElement;
    private int width;
    public int height;

    public Frame(String title, UiElement innerElement) {
        this.title = title;
        this.innerElement = innerElement;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void calculateSize() {
        innerElement.calculateSize(); //узнаем размер вложенного контейнера
        this.width = innerElement.getWidth() + 2; // Добавляем отступы для рамки
        this.height = innerElement.getHeight() + 2;
    }

    @Override
    public void render() {
        System.out.println("╔" + title + "═".repeat(width - title.length() - 1) + "╗");
        innerElement.render(); // Рендеринг вложенного элемента
        System.out.println("╚" + "═".repeat(width) + "╝");
    }
}

class Rectangle implements UiElement {
    private final UiElement innerElement;
    private int width;
    private int height;

    public Rectangle(UiElement innerElement) {
        this.innerElement = innerElement;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void calculateSize() {
        innerElement.calculateSize();
        this.width = innerElement.getWidth() + 2; //отступы на рамку
        this.height = innerElement.getHeight() + 2;
    }

    @Override
    public void render() {
        System.out.println("┌" + "─".repeat(width) + "┐");
        innerElement.render();
        System.out.println("└" + "─".repeat(width) + "┘");
    }
}

class VerticalLayout implements UiElement {
    private List<UiElement> elements = new ArrayList<>();
    private int height;
    private int width;

    public void addElement(UiElement element) {
        elements.add(element);
    }

    @Override
    public void calculateSize() {
        width = 0;
        height = 0;
        for (UiElement element : elements) {
            element.calculateSize();
            width = Math.max(width, element.getWidth());
            height += element.getHeight();
        }
    }

    @Override
    public void render() {
        for (UiElement element : elements) {
            element.render();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}

class HorizontalLayout implements UiElement {
    private List<UiElement> elements = new ArrayList<>();
    private int width;
    private int height;

    public void addElement(UiElement element) {
        elements.add(element);
    }

    @Override
    public void calculateSize() {
        width = 0;
        height = 0;
        for (UiElement element : elements) {
            element.calculateSize();
            width += element.getWidth(); // Ширина — сумма всех ширин
            height = Math.max(height, element.getHeight()); // Высота — максимальная из всех
        }
    }

    @Override
    public void render() {
        // Каждый элемент рендерится на одной линии
        for (UiElement element : elements) {
            element.render();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
