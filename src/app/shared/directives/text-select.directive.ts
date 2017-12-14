import {Directive, ElementRef, HostListener, AfterViewInit} from '@angular/core';

@Directive({
  selector: '[appTextSelect]',
})
export class TextSelectDirective implements AfterViewInit {
  content: string;

  @HostListener('keydown', ['$event']) disableKeydown(e) {
    if (!e.ctrlKey || 'xv'.includes(e.key)) {
      e.preventDefault();
    }
  }

  @HostListener('input') disableInput() {
    console.log(123);
    this.ref.nativeElement.innerHTML = this.content;
  }

  constructor(private ref: ElementRef) {
    this.ref.nativeElement.contentEditable = true;
  }

  ngAfterViewInit() {
    this.content = this.ref.nativeElement.innerHTML;
  }
}
