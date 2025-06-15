import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service'; // adjust the path if needed

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent implements OnInit {

selectedFile!: File;
  results: any[] = [];
  displayedColumns: string[] = ['empId1', 'empId2', 'daysWorkedTogether'];

  constructor(private apiService: ApiService) {}

  ngOnInit(): void {
    
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

uploadFile() {
  if (!this.selectedFile) return;

  this.apiService.uploadFile(this.selectedFile).subscribe({
    next: () => {
 
        this.apiService.getResults().subscribe({
          next: (data: any) => {
            this.results = data;
          },
          error: (err: any) => {
            console.error('Error fetching results', err);
          }
        }); 
      },
    error: (err: any) => {
      console.error('Upload failed', err);
    }
  });
}

}
