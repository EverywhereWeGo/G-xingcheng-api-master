package com.bfd.api.utils;

import com.google.common.base.CharMatcher;

public class CommonUtils
{
  public static boolean isCategory(String hbaseColumn)
  {
    return (hbaseColumn.contains("|")) || (CharMatcher.DIGIT.matchesAllOf(hbaseColumn.substring(hbaseColumn.indexOf(":") + 1, hbaseColumn.lastIndexOf(":"))));
  }
}
