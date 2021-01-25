package com.sap.mervyn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LambdaTesst {
	public static void main(String[] args) {
		List<SectionData> sections = new ArrayList<>();
		SectionData section1 = new SectionData("section1");
		SectionData section2 = new SectionData("section2");
		SectionData section3 = new SectionData("section3");
		SectionData section4 = new SectionData("section4");
		sections.add(section1);
		sections.add(section2);
		sections.add(section3);
		sections.add(section4);
		
		FieldData field11 = new FieldData("field1");
		FieldData field12 = new FieldData("field2");
		FieldData field13 = new FieldData("field3");
		List<FieldData> fields1 = new ArrayList<>();
		fields1.add(field11);
		fields1.add(field12);
		fields1.add(field13);
		
		FieldData field22 = new FieldData("field2");
		FieldData field23 = new FieldData("field3");
		List<FieldData> fields2 = new ArrayList<>();
		fields2.add(field22);
		fields2.add(field23);
		
		FieldData field31 = new FieldData("field1");
		List<FieldData> fields3 = new ArrayList<>();
		fields3.add(field31);
		
		FieldData field43 = new FieldData("field3");
		List<FieldData> fields4 = new ArrayList<>();
		fields4.add(field43);
		
		section1.setFields(fields1);
		section2.setFields(fields2);
		section3.setFields(fields3);
		section4.setFields(fields4);
		
		
		sections = sections.stream().filter(section -> {
			Optional<FieldData> childFieldData = section.getFields().stream().filter(fieldData -> "field1".equals(fieldData.getId())).findFirst();
			if (childFieldData.isPresent()) {
				return true;
			}
			
			return false;
		}).collect(Collectors.toList());
		
		System.out.println(sections);
		
	}
	
	private static class SectionData {
		private String name;
		
		public SectionData(String name) {
			this.name = name;
		}
		
		List<FieldData> fields = new ArrayList<>();
		
		public void setFields(List<FieldData> fields) {
			this.fields.addAll(fields);
		}
		
		public List<FieldData> getFields() {
			return this.fields;
		}
		
		@Override
		public String toString() {
			return "Section: " + name;
		}
	}
	
	private static class FieldData {
		private String id;
		
		public FieldData(String id) {
			this.id = id;
		}
		
		public String getId() {
			return this.id;
		}
	}
}


