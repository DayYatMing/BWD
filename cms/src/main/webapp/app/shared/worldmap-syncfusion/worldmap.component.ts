import { Component, OnInit, ViewChild } from '@angular/core';
import {
  MapsComponent,
  LegendService,
  MapsModule,
  NavigationLineService,
  MapsTooltipService,
  MarkerService,
  ZoomService,
  DataLabelService,
  BubbleService,
  SelectionService,
  AnnotationsService,
} from '@syncfusion/ej2-angular-maps';
import { world_map } from './world-map';
import { GridAllModule } from '@syncfusion/ej2-angular-grids';
import SharedModule from '../shared.module';

@Component({
  selector: 'syncfusion-worldmap',
  templateUrl: './worldmap.component.html',
  styleUrl: './worldmap.component.css',
  imports: [SharedModule, MapsModule, GridAllModule],
  providers: [
    LegendService,
    MarkerService,
    MapsTooltipService,
    DataLabelService,
    BubbleService,
    NavigationLineService,
    SelectionService,
    AnnotationsService,
    ZoomService
  ],
})
export class WorldMapComponentSync implements OnInit {
  @ViewChild('maps')
  public maps?: MapsComponent;

  public shapeData?: object;
  public navigationLineSettings?: object[];
  public tooltipSettings?: object;
  public markerSettings?: object;
  public zoomSettings?: object;
  public legendSettings?: object;
  public dataSource?: object[];
  public shapePropertyPath = 'name';
  public shapeDataPath = 'continent';
  public shapeSettings?: object;
  // public centerPosition = { latitude: 0, longitude: -180 };

  //initializing default settings for world map below
  ngOnInit(): void {
    this.dataSource = [];
    this.tooltipSettings = {
      visible: true,
      valuePath: 'name',
    };
    this.zoomSettings = { enable: true, zoomFactor: 1, centerPosition: { latitude: 0, longitude: -160 } };
    this.navigationLineSettings = [
      {
        visible: true,
        latitude: [],
        longitude: [],
        color: 'blue',
        angle: 0,
        width: 2,
        dashArray: '1',
      },
    ];
    this.markerSettings = [
      {
        visible: true,
        colorValuePath: 'color',
        shape: 'Circle',
        template: '<div class="blinking-marker"></div>',
        animationDuration: 0,
        dataSource: [],
        tooltipSettings: {
          visible: true,
          valuePath: 'city',
        },
      },
    ];
    this.shapeSettings = {
      autofill: true,
      colorMapping: [],
    };
    this.legendSettings = {
      visible: true,
      position: 'Right',
    };
    this.shapeData = world_map;
  }
}

