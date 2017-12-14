import {Directive, ElementRef, HostListener, Input, AfterViewInit} from '@angular/core';

@Directive({
  selector: '[appSyncScroll]',
})
export class SyncScrollDirective implements AfterViewInit {
  @Input('appSyncScroll') boundElement: any;

  elemScrollDistance: number;
  boundElemScrollDistance: number;

  @HostListener('mouseenter') allowScroll() {
    this.ref.nativeElement.disableScrollEvent = false;
  }

  @HostListener('scroll') synchronizeScroll() {
    if (this.boundElement && !this.ref.nativeElement.disableScrollEvent
      && this.elemScrollDistance > 0 && this.boundElemScrollDistance > 0) {
      const scrolled: number = this.ref.nativeElement.scrollTop / this.elemScrollDistance;
      this.boundElement.scrollTop = scrolled * this.boundElemScrollDistance;
      this.boundElement.disableScrollEvent = true;
    }
  }

  constructor(private ref: ElementRef) {}

  ngAfterViewInit() {
    this.elemScrollDistance = this.ref.nativeElement.scrollHeight - this.ref.nativeElement.clientHeight;
    this.boundElemScrollDistance = this.boundElement.scrollHeight - this.boundElement.clientHeight;
  }
}
