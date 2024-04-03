package com.daniela.expensemanagement.controllers;

import com.daniela.expensemanagement.model.DailyExpense;
import com.daniela.expensemanagement.model.MonthlyExpense;
import com.daniela.expensemanagement.model.YearlyExpense;
import com.daniela.expensemanagement.services.interfaces.StatisticService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.TextStyle;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class StatisticController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ChoiceBox<String> monthChoiceBox;

    @FXML
    private ChoiceBox<String> yearsChoiceBox;

    @FXML
    private ComboBox<String> combo;
    private final StatisticService statisticService;

    @FXML
    void show(ActionEvent event) {
        String month = monthChoiceBox.getValue();
        String year = yearsChoiceBox.getValue();

        log.info("month {} and year {}",month, year);
        xAxis.getCategories().clear();


        if(!month.equals("all") && !year.equals("all")){
            List<DailyExpense> dailyExpenses = statisticService.dailyExpense(Integer.valueOf(year), Integer.valueOf(Month.valueOf(month.toUpperCase()).getValue()));

            XYChart.Series<String, Number> series = new XYChart.Series<>();

            dailyExpenses.forEach(dailyExpense -> {
                series.getData().add(new XYChart.Data<>(String.valueOf(dailyExpense.getLocalDate()), dailyExpense.getTotal()));
                xAxis.getCategories().add(String.valueOf(dailyExpense.getLocalDate()));
            });

            barChart.getData().clear();
            barChart.getData().add(series);

        } else if (month.equals("all") && !year.equals("all")) {
            List<MonthlyExpense> monthlyExpenses = statisticService.monthlyExpense(Integer.valueOf(year));

            XYChart.Series<String, Number> series = new XYChart.Series<>();

            monthlyExpenses.forEach(monthlyExpense -> {
                series.getData().add(new XYChart.Data<>(String.valueOf(monthlyExpense.getMonth()), monthlyExpense.getTotal()));
                xAxis.getCategories().add(String.valueOf(monthlyExpense.getMonth()));
            });

            barChart.getData().clear();
            barChart.getData().add(series);

        }else if (year.equals("all")){
            List<YearlyExpense> yearlyExpenses = statisticService.yearlyExpense();

            XYChart.Series<String, Number> series = new XYChart.Series<>();

            yearlyExpenses.forEach(yearlyExpense -> {
                series.getData().add(new XYChart.Data<>(String.valueOf(yearlyExpense.getYear()), yearlyExpense.getTotal()));
                xAxis.getCategories().add(String.valueOf(yearlyExpense.getYear()));

            });

            barChart.getData().clear();
            barChart.getData().add(series);
        }
    }

    private void initializeMonths(){
        List<String> months = EnumSet.allOf(Month.class)
                .stream().map(d -> d.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
                .collect(Collectors.toList());

        months.add("all");

        monthChoiceBox.getItems().addAll(months);
        monthChoiceBox.setValue(months.stream().findFirst().orElse(null));
        combo.getItems().addAll(months);
        combo.setEditable(true);
    }

    private void initializeYears(int begin){
        List<String> years = IntStream
                .rangeClosed(begin, Year.now().getValue()).mapToObj(String::valueOf)
                .collect(Collectors.toList());
        years.add("all");
        yearsChoiceBox.getItems().addAll(years);
        yearsChoiceBox.setValue(years.stream().findFirst().orElse(null));
    }

    private void changeStatusOfMonthChoiceBox(ChoiceBox<String> monthOfYears, ChoiceBox<String> years){
        years.getSelectionModel().selectedItemProperty().addListener(e->{
            monthOfYears.setDisable(years.getSelectionModel().getSelectedItem().equals("all"));
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeMonths();
        initializeYears(LocalDate.now().getYear());

        changeStatusOfMonthChoiceBox(monthChoiceBox, yearsChoiceBox);
        xAxis.setAnimated(false);
        barChart.setLegendVisible(false);
    }
}
