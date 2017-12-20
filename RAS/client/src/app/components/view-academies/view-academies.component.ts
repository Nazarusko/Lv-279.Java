import {Component, OnInit, ViewChild} from '@angular/core';
import {AcademyService} from "./academy.service";
import {PagerService} from "./pager.service";
import {FilterService} from "./filter.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {SearchBarService} from "./search-bar.service";
import {Form} from "@angular/forms";

@Component({
  selector: 'app-view-academies',
  templateUrl: './view-academies.component.html',
  styleUrls: ['./view-academies.component.css'],
  providers: [AcademyService, PagerService, FilterService]
})
export class ViewAcademiesComponent implements OnInit {
  academies = [];
  filteredAcademies = [];

  displayedColumns =
    ['grName', 'nameForSite', 'technologyName', 'profileName',
      'paymentStatus', 'cityName', 'startDate', 'endDate', 'academyStage',
      'experts', 'studentPlannedToGraduate', 'studentPlannedToEnrollment',
      'studentsActual', 'hiredNotGraduated', 'directionName', 'interviewerFeedback'
    ];
  dataSource: any;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;


  constructor(private academyService: AcademyService,
              private filterService: FilterService) {
  }

  ngOnInit() {
    this.academyService.getAll().subscribe(
      data => {
        this.academies = data;
        this.academies = this.academies.slice(0, data.length - 2);
        this.filteredAcademies = this.academies;

        this.dataSource = new MatTableDataSource(this.filteredAcademies);

        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error => console.log(error)
    );
  }

  onFilterField(form) {
    this.filteredAcademies = this.academies;
    Object.keys(form.form.value).forEach(key => {
      if (key === 'startDate' || key === 'endDate') {
        this.filteredAcademies = this.filterService.transformDate(this.filteredAcademies, form.form.value[key], key);
      }else {
        this.filteredAcademies = this.filterService.transform(this.filteredAcademies, form.form.value[key], key);
      }
      this.dataSource = new MatTableDataSource<Element>(this.filteredAcademies);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
}
