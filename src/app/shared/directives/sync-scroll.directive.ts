import {Directive, ElementRef, HostListener, Input} from '@angular/core';

@Directive({
  selector: '[appSyncScroll]',
})
export class SyncScrollDirective {
  @Input('syncScroll') boundElement: any;

  @HostListener('scroll') synchronizeScroll() {
    if (this.boundElement) {
      const elemScrollDistance: number = this.ref.nativeElement.scrollHeight - this.ref.nativeElement.clientHeight;
      if (elemScrollDistance <= 0) {
        return;
      }
      const boundElemScrollDistance: number = this.boundElement.scrollHeight - this.boundElement.clientHeight;
      if (boundElemScrollDistance <= 0) {
        return;
      }
      const scrolled: number = this.ref.nativeElement.scrollTop / elemScrollDistance;
      this.boundElement.scrollTop = scrolled * boundElemScrollDistance;
    }
  }

  constructor(private ref: ElementRef) {}
}
