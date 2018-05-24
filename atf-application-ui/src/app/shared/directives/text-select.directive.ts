import {Directive, ElementRef, HostListener, AfterViewInit} from '@angular/core';

@Directive({
  selector: '[appTextSelect]',
})
export class TextSelectDirective implements AfterViewInit {

  content: string;

  @HostListener('keydown', ['$event']) disableTextChangingEvents(e) {
    if (e.ctrlKey && 'zxv'.includes(e.key) || [8, 46].includes(e.keyCode)) {
      e.preventDefault();
    }
  }

  @HostListener('keypress', ['$event']) disableKeypress(e) {
    e.preventDefault();
  }

  @HostListener('input') disableInput(e) {
    this.ref.nativeElement.innerHTML = this.content;
  }

  constructor(private ref: ElementRef) {
    this.ref.nativeElement.contentEditable = true;
  }

  ngAfterViewInit() {
    this.content = this.ref.nativeElement.innerHTML;
  }
}
