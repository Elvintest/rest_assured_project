package ru.atc.fgislk.ppod.testcore.lklback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;
/**
 * ForestObject
 */
@Getter
@Setter
@Builder
public class ForestObject {
  private String id = null;
  private DictionaryItem forestry = null;
  private DictionaryItem subforestry = null;
  private DictionaryItem tract = null;
  private String quarter = null;
  private String taxationUnit = null;
  private String cuttingArea = null;
  private String usageType = null;
  private HarvestingWoodInfo harvestingWood = null;
  private HarvestingObjectInfo harvestingObject = null;
  private OtherUsageTypesInfo otherUsageTypes = null;
  private OtherUsageObjectsInfo otherUsageObjects = null;
  @Singular
  private List<Coordinate> coordinates = null;
  private DocumentInfo schemeDocument = null;
}
