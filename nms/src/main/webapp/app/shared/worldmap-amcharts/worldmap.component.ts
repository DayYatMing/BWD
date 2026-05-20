import * as am4core from '@amcharts/amcharts4/core';
import * as am4charts from '@amcharts/amcharts4/charts';

import am4themes_animated from '@amcharts/amcharts4/themes/animated';
import am4themes_material from '@amcharts/amcharts4/themes/material';
import * as am4maps from '@amcharts/amcharts4/maps';
import am4geodata_worldLow from '@amcharts/amcharts4-geodata/worldLow';
import { Component, OnDestroy, OnInit } from '@angular/core';
import * as _ from 'lodash';

am4core.useTheme(am4themes_animated);
am4core.useTheme(am4themes_material);

@Component({
  selector: 'amcharts-worldmap',
  templateUrl: './worldmap.component.html',
  styleUrls: ['worldmap.css'],
})
export class WorldMapComponentAm implements OnInit, OnDestroy {
  public imageSeriesInstance: any;
  public chart: any;
  public siteData: any;
  public polygonSeries: any;

  ngOnDestroy(): void {
    this.chart.dispose();
  }

  ngOnInit(): void {
    this.loadChart();
  }

  loadChart() {
    let chart: any = am4core.create('chartdiv', am4maps.MapChart);
    chart.geodata = am4geodata_worldLow;
    chart.projection = new am4maps.projections.NaturalEarth1();
    this.chart = chart;

    // Create map polygon series
    var polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());
    this.polygonSeries = polygonSeries;

    // Make map load polygon (like country names) data from GeoJSON
    polygonSeries.useGeodata = true;

    polygonSeries.mapPolygons.template.fillOpacity = 0.6;
    polygonSeries.mapPolygons.template.nonScalingStroke = true;
    polygonSeries.mapPolygons.template.strokeWidth = 0.5;
    polygonSeries.mapPolygons.template.adapter.add('fill', function (fill: any, target: any) {
      return chart.colors.getIndex(Math.round(Math.random() * 4)).saturate(0.9);
    });

    chart.smallMap = new am4maps.SmallMap();
    chart.smallMap.rectangle.stroke = am4core.color('#367B25');
    chart.smallMap.rectangle.strokeWidth = 2;
    chart.smallMap.background.stroke = am4core.color('#7B3625');
    chart.smallMap.background.strokeOpacity = 1;
    chart.smallMap.background.fillOpacity = 0.9;
    chart.smallMap.valign = 'top';
    chart.smallMap.series.push(polygonSeries);

    // Configure series
    var polygonTemplate = polygonSeries.mapPolygons.template;
    polygonTemplate.tooltipText = '{name}';
    polygonSeries.exclude = ['AQ'];

    // Create hover state and set alternative fill color
    var hs = polygonTemplate.states.create('hover');
    hs.properties.fill = chart.colors.getIndex(1);

    // Center on Pacic
    chart.deltaLongitude = -214.8;
    chart.homeZoomLevel = 1;

    var graticuleSeries = chart.series.push(new am4maps.GraticuleSeries());
    graticuleSeries.fitExtent = false;

    var imageSeries = chart.series.push(new am4maps.MapImageSeries());
    this.imageSeriesInstance = imageSeries;
    var imageTemplate = imageSeries.mapImages.template;
    imageTemplate.propertyFields.longitude = 'longitude';
    imageTemplate.propertyFields.latitude = 'latitude';
    imageTemplate.nonScaling = true;

    imageSeries.mapImages.template.tooltipText = '[bold]{labelname}[/]:[font-size:10px]\n{comment}';
    imageSeries.tooltip.showInViewport = false;
    imageSeries.tooltip.background.fillOpacity = 0.4;
    imageSeries.tooltip.getStrokeFromObject = true;
    imageSeries.tooltip.getFillFromObject = false;
    imageSeries.tooltip.background.fill = am4core.color('#000000');

    let circle = imageSeries.mapImages.template.createChild(am4core.Circle);
    circle.radius = 3;
    circle.propertyFields.fill = 'color';

    let circle2 = imageSeries.mapImages.template.createChild(am4core.Circle);
    circle2.radius = 3;
    circle2.propertyFields.fill = 'color';

    circle2.events.on('inited', function (event: any) {
      animateBullet(event.target);
    });

    function animateBullet(circle: any) {
      let animation = circle.animate(
        [
          { property: 'scale', from: 1, to: 5 },
          { property: 'opacity', from: 1, to: 0 },
        ],
        2000,
        am4core.ease.circleOut,
      );
      animation.events.on('animationended', function (event: any) {
        animateBullet(event.target.object);
      });
    }

    let button = chart.chartContainer.createChild(am4core.Button);
    button.padding(5, 5, 5, 5);
    button.align = 'left';
    button.events.on('hit', function (this: any, event: any) {
      chart.goHome();
    });
    button.icon = new am4core.Sprite();
    button.icon.path = 'M16,8 L14,8 L14,16 L10,16 L10,10 L6,10 L6,16 L2,16 L2,8 L0,8 L8,0 L16,8 Z M16,8';
  }

  public showLegend() {
    this.chart.legend = new am4charts.Legend();
    this.chart.legend.position = 'right';
    this.chart.legend.align = 'right';
    this.chart.legend.scrollable = true;
    this.chart.legend.maxWidth = undefined;
    this.chart.legend.useDefaultMarker = true;
    let marker = this.chart.legend.markers.template.children.getIndex(0);
    marker.cornerRadius(12, 12, 12, 12);
    marker.strokeWidth = 2;
    marker.strokeOpacity = 1;
    marker.stroke = am4core.color('#ccc');
    var marker1 = this.chart.legend.markers.template;
    this.chart.legend.labels.template.truncate = false;
    this.chart.legend.labels.template.wrap = true;
    //  this.chart.legend.itemContainers.template.tooltipText = "{name}";

    var flag = marker1.createChild(am4core.Image);
    flag.adapter.add('href', function (href: any, target: any) {
      if (!(target.dataItem && target.dataItem.dataContext && target.dataItem.dataContext.name)) {
        target.dataItem.visible = false;
      }
    });

    this.chart.radius = am4core.percent(85);
  }

  public drawLine(name: any, color: any, data: any) {
    var lineSeries = this.chart.series.push(new am4maps.MapLineSeries());
    //lineSeries.mapLines.template.strokeWidth = 2;
    lineSeries.fill = am4core.color(color);
    var line = lineSeries.mapLines.create();
    lineSeries.strokeWidth = 4;
    line.multiGeoLine = data;
    line.stroke = am4core.color(color);
    lineSeries.name = name;
    line.shortestDistance = true;
    line.tooltipText = name;
  }

  public refreshLine() {
    this.siteData = _.cloneDeep(this.imageSeriesInstance.data);
    this.ngOnDestroy();
    this.ngOnInit();
    this.imageSeriesInstance.data = this.siteData;
    //   this.showLegend();
  }
}
