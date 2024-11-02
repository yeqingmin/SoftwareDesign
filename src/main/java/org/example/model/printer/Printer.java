package org.example.model.printer;

import org.example.model.html.HTMLTag;

public interface Printer<T> {
    String format(T root);
}