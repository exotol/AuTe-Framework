import {Directive, ElementRef, HostListener, Input} from '@angular/core';

@Directive({
  selector: '[appSyncScroll]',
})
export class SyncScrollDirective {
  @Input('appSyncScroll') boundElement: any;

  @HostListener('mouseenter') allowScroll() {
    this.ref.nativeElement.disableScrollEvent = false;
  }

  @HostListener('scroll') synchronizeScroll() {
    if (this.boundElement && !this.ref.nativeElement.disableScrollEvent) {
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
      this.boundElement.disableScrollEvent = true;
    }
  }

  constructor(private ref: ElementRef) {}
}
