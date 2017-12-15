import {Directive, ElementRef, HostListener} from '@angular/core';

@Directive({
  selector: '[appTextSelect]',
})
export class TextSelectDirective {

  @HostListener('keydown', ['$event']) overrideSelectAllShortcut(e) {
    if (e.ctrlKey && e.key === 'a') {
      e.preventDefault();
      const range = document.createRange();
      range.selectNode(this.ref.nativeElement);
      window.getSelection().removeAllRanges();
      window.getSelection().addRange(range);
    }
  }

  constructor(private ref: ElementRef) {
    this.ref.nativeElement.tabIndex = 0;
  }
}
