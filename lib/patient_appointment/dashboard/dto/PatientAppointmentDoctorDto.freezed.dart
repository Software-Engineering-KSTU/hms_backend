// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'PatientAppointmentDoctorDto.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$PatientAppointmentDoctorDto {

 String get doctorId; String get date; String get symptomsDescription; String get selfTreatmentMethodsTaken;
/// Create a copy of PatientAppointmentDoctorDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$PatientAppointmentDoctorDtoCopyWith<PatientAppointmentDoctorDto> get copyWith => _$PatientAppointmentDoctorDtoCopyWithImpl<PatientAppointmentDoctorDto>(this as PatientAppointmentDoctorDto, _$identity);

  /// Serializes this PatientAppointmentDoctorDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is PatientAppointmentDoctorDto&&(identical(other.doctorId, doctorId) || other.doctorId == doctorId)&&(identical(other.date, date) || other.date == date)&&(identical(other.symptomsDescription, symptomsDescription) || other.symptomsDescription == symptomsDescription)&&(identical(other.selfTreatmentMethodsTaken, selfTreatmentMethodsTaken) || other.selfTreatmentMethodsTaken == selfTreatmentMethodsTaken));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,doctorId,date,symptomsDescription,selfTreatmentMethodsTaken);

@override
String toString() {
  return 'PatientAppointmentDoctorDto(doctorId: $doctorId, date: $date, symptomsDescription: $symptomsDescription, selfTreatmentMethodsTaken: $selfTreatmentMethodsTaken)';
}


}

/// @nodoc
abstract mixin class $PatientAppointmentDoctorDtoCopyWith<$Res>  {
  factory $PatientAppointmentDoctorDtoCopyWith(PatientAppointmentDoctorDto value, $Res Function(PatientAppointmentDoctorDto) _then) = _$PatientAppointmentDoctorDtoCopyWithImpl;
@useResult
$Res call({
 String doctorId, String date, String symptomsDescription, String selfTreatmentMethodsTaken
});




}
/// @nodoc
class _$PatientAppointmentDoctorDtoCopyWithImpl<$Res>
    implements $PatientAppointmentDoctorDtoCopyWith<$Res> {
  _$PatientAppointmentDoctorDtoCopyWithImpl(this._self, this._then);

  final PatientAppointmentDoctorDto _self;
  final $Res Function(PatientAppointmentDoctorDto) _then;

/// Create a copy of PatientAppointmentDoctorDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? doctorId = null,Object? date = null,Object? symptomsDescription = null,Object? selfTreatmentMethodsTaken = null,}) {
  return _then(_self.copyWith(
doctorId: null == doctorId ? _self.doctorId : doctorId // ignore: cast_nullable_to_non_nullable
as String,date: null == date ? _self.date : date // ignore: cast_nullable_to_non_nullable
as String,symptomsDescription: null == symptomsDescription ? _self.symptomsDescription : symptomsDescription // ignore: cast_nullable_to_non_nullable
as String,selfTreatmentMethodsTaken: null == selfTreatmentMethodsTaken ? _self.selfTreatmentMethodsTaken : selfTreatmentMethodsTaken // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [PatientAppointmentDoctorDto].
extension PatientAppointmentDoctorDtoPatterns on PatientAppointmentDoctorDto {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _PatientAppointmentDoctorDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _PatientAppointmentDoctorDto() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _PatientAppointmentDoctorDto value)  $default,){
final _that = this;
switch (_that) {
case _PatientAppointmentDoctorDto():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _PatientAppointmentDoctorDto value)?  $default,){
final _that = this;
switch (_that) {
case _PatientAppointmentDoctorDto() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String doctorId,  String date,  String symptomsDescription,  String selfTreatmentMethodsTaken)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _PatientAppointmentDoctorDto() when $default != null:
return $default(_that.doctorId,_that.date,_that.symptomsDescription,_that.selfTreatmentMethodsTaken);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String doctorId,  String date,  String symptomsDescription,  String selfTreatmentMethodsTaken)  $default,) {final _that = this;
switch (_that) {
case _PatientAppointmentDoctorDto():
return $default(_that.doctorId,_that.date,_that.symptomsDescription,_that.selfTreatmentMethodsTaken);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String doctorId,  String date,  String symptomsDescription,  String selfTreatmentMethodsTaken)?  $default,) {final _that = this;
switch (_that) {
case _PatientAppointmentDoctorDto() when $default != null:
return $default(_that.doctorId,_that.date,_that.symptomsDescription,_that.selfTreatmentMethodsTaken);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _PatientAppointmentDoctorDto implements PatientAppointmentDoctorDto {
  const _PatientAppointmentDoctorDto({required this.doctorId, required this.date, required this.symptomsDescription, required this.selfTreatmentMethodsTaken});
  factory _PatientAppointmentDoctorDto.fromJson(Map<String, dynamic> json) => _$PatientAppointmentDoctorDtoFromJson(json);

@override final  String doctorId;
@override final  String date;
@override final  String symptomsDescription;
@override final  String selfTreatmentMethodsTaken;

/// Create a copy of PatientAppointmentDoctorDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$PatientAppointmentDoctorDtoCopyWith<_PatientAppointmentDoctorDto> get copyWith => __$PatientAppointmentDoctorDtoCopyWithImpl<_PatientAppointmentDoctorDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$PatientAppointmentDoctorDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _PatientAppointmentDoctorDto&&(identical(other.doctorId, doctorId) || other.doctorId == doctorId)&&(identical(other.date, date) || other.date == date)&&(identical(other.symptomsDescription, symptomsDescription) || other.symptomsDescription == symptomsDescription)&&(identical(other.selfTreatmentMethodsTaken, selfTreatmentMethodsTaken) || other.selfTreatmentMethodsTaken == selfTreatmentMethodsTaken));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,doctorId,date,symptomsDescription,selfTreatmentMethodsTaken);

@override
String toString() {
  return 'PatientAppointmentDoctorDto(doctorId: $doctorId, date: $date, symptomsDescription: $symptomsDescription, selfTreatmentMethodsTaken: $selfTreatmentMethodsTaken)';
}


}

/// @nodoc
abstract mixin class _$PatientAppointmentDoctorDtoCopyWith<$Res> implements $PatientAppointmentDoctorDtoCopyWith<$Res> {
  factory _$PatientAppointmentDoctorDtoCopyWith(_PatientAppointmentDoctorDto value, $Res Function(_PatientAppointmentDoctorDto) _then) = __$PatientAppointmentDoctorDtoCopyWithImpl;
@override @useResult
$Res call({
 String doctorId, String date, String symptomsDescription, String selfTreatmentMethodsTaken
});




}
/// @nodoc
class __$PatientAppointmentDoctorDtoCopyWithImpl<$Res>
    implements _$PatientAppointmentDoctorDtoCopyWith<$Res> {
  __$PatientAppointmentDoctorDtoCopyWithImpl(this._self, this._then);

  final _PatientAppointmentDoctorDto _self;
  final $Res Function(_PatientAppointmentDoctorDto) _then;

/// Create a copy of PatientAppointmentDoctorDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? doctorId = null,Object? date = null,Object? symptomsDescription = null,Object? selfTreatmentMethodsTaken = null,}) {
  return _then(_PatientAppointmentDoctorDto(
doctorId: null == doctorId ? _self.doctorId : doctorId // ignore: cast_nullable_to_non_nullable
as String,date: null == date ? _self.date : date // ignore: cast_nullable_to_non_nullable
as String,symptomsDescription: null == symptomsDescription ? _self.symptomsDescription : symptomsDescription // ignore: cast_nullable_to_non_nullable
as String,selfTreatmentMethodsTaken: null == selfTreatmentMethodsTaken ? _self.selfTreatmentMethodsTaken : selfTreatmentMethodsTaken // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}

// dart format on
