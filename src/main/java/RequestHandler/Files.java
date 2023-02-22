package RequestHandler;

public enum Files {
    index("src/main/static/index.html"),
    page1("src/main/static/page1.html"),
    page2("src/main/static/page2.html"),
    page3("src/main/static/page3.html");

    String filepath;
    Files(String filepath){
        this.filepath = filepath;
    }
}
