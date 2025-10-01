update_plot_svg <- function(value, file_path="src/main/resources/static/plot.svg") {
  y_values[current_index] <<- value
  current_index <<- current_index + 1

  svg(filename = file_path, width = 9, height = 6)

  print(xyplot(
    tail(y_values, 30) ~ tail(seq_along(y_values), 30),
    type = "l",
    col.line = "brown",
    xlab = "Index",
    ylab = "Value",
    main = "Dynamic Plot (Last 30 Points)",
    panel = function(...) {
      panel.grid(h = -1, v = -1, col = "lightgray")
      panel.xyplot(...)
    }
  ))

  dev.off()
}
