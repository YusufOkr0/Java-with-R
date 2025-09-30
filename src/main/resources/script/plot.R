library(lattice)

# Global veri vektörü
y_values <- numeric(100)  # 100 elemanlı vektör
x_values <- 0:99          # x-axis 0-99
current_index <- 1        # sıradaki konum, R 1-indexed

update_plot_svg <- function(value, file_path="src/main/resources/static/plot.svg") {
  y_values[current_index] <<- value
  current_index <<- current_index + 1

  svg(filename = file_path, width = 6, height = 4)

  print(xyplot(
    y_values ~ x_values,
    type = "l",
    col.line = "brown",
    xlab = "Index",
    ylab = "Value",
    main = "Dynamic Plot from Java Data",
    panel = function(...) {
      panel.grid(h = -1, v = -1, col = "lightgray")  # grid ekli
      panel.xyplot(...)
    }
  ))

  dev.off()
}
