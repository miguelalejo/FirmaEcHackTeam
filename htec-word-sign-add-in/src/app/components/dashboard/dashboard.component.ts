import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { SignRequest } from 'src/app/model/sign-request';
import { SignResponse } from 'src/app/model/sign-response';
import { SignService } from 'src/app/services/sign.service';
import { environment } from '../../../environments/environment';

declare var Utils: any;
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  @ViewChild("terminal")
  terminal: ElementRef;
  title = 'HackTeamEC Signer';
  signRequest: SignRequest;
  signResponse: SignResponse;
  messages: Array<string> = [];
  serverStarted: boolean = false;

  constructor(private signService: SignService) {
    this.signRequest = new SignRequest();
  }

  ngOnInit(): void {
    this.ping();
  }

  public sign() {
    this.showMessage(`Obteniendo nombre del archivo`);
    let object = this;
    this.signRequest.name = "";
    Office.context.document.getFilePropertiesAsync(function (asyncResult: Office.AsyncResult<Office.FileProperties>) {
      var fileUrl = asyncResult.value.url;
      if (fileUrl) {
        object.signRequest.name = fileUrl.replace(/^.*[\\\/]/, '');
        object.messages.push(`Archivo: ${object.signRequest.name}`);
      }
      object.readDocument();
    });

  }

  private readDocument() {
    this.showMessage(`Leyendo el documento...`);
    Office.context.document.getFileAsync(Office.FileType.Compressed, { sliceSize: 65536 },
      (result: Office.AsyncResult<Office.File>) => {
        if (result.status == Office.AsyncResultStatus.Succeeded) {
          this.getSliceAsync(result.value, 0, result.value.sliceCount, true, [], 0);
        } else {
          console.log(`Error: ${result.error.message}`);
        }
      });
  }


  private getSliceAsync(file: Office.File, nextSlice, sliceCount: number, gotAllSlices: boolean, docdataSlices, slicesReceived) {
    file.getSliceAsync(nextSlice, (sliceResult) => {
      if (sliceResult.status == Office.AsyncResultStatus.Succeeded) {
        if (!gotAllSlices) {
          return;
        }
        docdataSlices[sliceResult.value.index] = sliceResult.value.data;
        if (++slicesReceived === sliceCount) {
          file.closeAsync();
          this.sendFile(docdataSlices);
        } else {
          this.getSliceAsync(file, ++nextSlice, sliceCount, gotAllSlices, docdataSlices, slicesReceived);
        }
      } else {
        gotAllSlices = false;
        file.closeAsync();
        console.log(`getSliceAsync Error: ${sliceResult.error.message}`);
      }
    });
  }

  sendFile(docdataSlices: []) {
    this.showMessage(`Codificando archivo`);
    let bytes = [];
    for (let i = 0; i < docdataSlices.length; i++) {
      bytes = bytes.concat(docdataSlices[i]);
    }
    this.signRequest.document = Utils.toHexString(bytes);
    this.showMessage(`Solicitando firma...`);
    this.signService.sing(this.signRequest).subscribe(
      (data) => {
        this.signResponse = data;
        if (this.signResponse.code == "000") {
          this.showMessage(`Archivo firmado`);
        }
        else {
          this.showMessage(`Error ${this.signResponse.code}: ${this.signResponse.message}`);
        }

      },
      (error) => console.error(error)
    );
  }

  validate() {
    Office.context.ui.openBrowserWindow(`${environment.endpoint}/servicio-firma/firmar/word/${this.signResponse.document}`);
  }

  handleUpload(event) {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      this.signRequest.key = Utils.b64tohex(reader.result.toString().split(",")[1]);
      this.showMessage(`Archivo ${file.name} cargado.`);
    };
  }

  private showMessage(message: string) {
    this.messages.push(message);
    setTimeout(() => {
      this.terminal.nativeElement.scrollTop = this.terminal.nativeElement.scrollHeight;
    }, 100);
  }

  private ping() {
    if(!this.serverStarted){
      this.showMessage("Verificando servidor disponible...")
    }
    this.signService.ping().subscribe(
      (data) => {
        if (data.code == "000") {
          if(!this.serverStarted){
            this.messages = [];
            this.showMessage("Servidor disponible...")
          }
          this.serverStarted = true;
        }
        setTimeout(() => {
          this.ping();
        }, 5000);
      },
      (error) => {
        this.serverStarted = false;
        setTimeout(() => {
          this.showMessage("Servidor no disponible");
          this.ping();
        }, 1000);
      }
    );
  }

}
