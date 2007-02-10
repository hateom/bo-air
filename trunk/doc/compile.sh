#!/bin/bash

texi2dvi bo-doc.tex || echo "!! texi2dvi ERROR!";
dvipdf bo-doc.dvi || echo "!! dvipdf ERROR!";

