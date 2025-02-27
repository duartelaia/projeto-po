package xxl.cellNetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import xxl.content.Content;

public class Cell implements Serializable, Subject, Observer {

    private Content _content = null;

    private List<Observer> _observers = new ArrayList<>();

    public Cell() {
    }

    public Cell(Content content) {
        _content = content;
    }

    @Override
    public void addObserver(Observer observer) {
        _observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        _observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : _observers) {
            observer.updateCell();
        }
    }

    @Override
    public void updateCell() {
        if(!hasEmptyContent())
            _content.updateContent();
        notifyObservers();
    }

    public void setContent(Content content) {
        _content = content;
        updateCell();
    }

    public Content getContent() {
        return _content;
    }

    public boolean hasEmptyContent() {
        return _content == null;
    }

}
