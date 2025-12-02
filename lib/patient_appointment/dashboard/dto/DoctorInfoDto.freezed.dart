// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'DoctorInfoDto.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$DoctorInfoDto {

@JsonKey(name: 'id') String get doctorId;@JsonKey(name: 'user', fromJson: _userFromJson) String get doctorName; String get specialization;
/// Create a copy of DoctorInfoDto
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$DoctorInfoDtoCopyWith<DoctorInfoDto> get copyWith => _$DoctorInfoDtoCopyWithImpl<DoctorInfoDto>(this as DoctorInfoDto, _$identity);

  /// Serializes this DoctorInfoDto to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is DoctorInfoDto&&(identical(other.doctorId, doctorId) || other.doctorId == doctorId)&&(identical(other.doctorName, doctorName) || other.doctorName == doctorName)&&(identical(other.specialization, specialization) || other.specialization == specialization));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,doctorId,doctorName,specialization);

@override
String toString() {
  return 'DoctorInfoDto(doctorId: $doctorId, doctorName: $doctorName, specialization: $specialization)';
}


}

/// @nodoc
abstract mixin class $DoctorInfoDtoCopyWith<$Res>  {
  factory $DoctorInfoDtoCopyWith(DoctorInfoDto value, $Res Function(DoctorInfoDto) _then) = _$DoctorInfoDtoCopyWithImpl;
@useResult
$Res call({
@JsonKey(name: 'id') String doctorId,@JsonKey(name: 'user', fromJson: _userFromJson) String doctorName, String specialization
});




}
/// @nodoc
class _$DoctorInfoDtoCopyWithImpl<$Res>
    implements $DoctorInfoDtoCopyWith<$Res> {
  _$DoctorInfoDtoCopyWithImpl(this._self, this._then);

  final DoctorInfoDto _self;
  final $Res Function(DoctorInfoDto) _then;

/// Create a copy of DoctorInfoDto
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? doctorId = null,Object? doctorName = null,Object? specialization = null,}) {
  return _then(_self.copyWith(
doctorId: null == doctorId ? _self.doctorId : doctorId // ignore: cast_nullable_to_non_nullable
as String,doctorName: null == doctorName ? _self.doctorName : doctorName // ignore: cast_nullable_to_non_nullable
as String,specialization: null == specialization ? _self.specialization : specialization // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [DoctorInfoDto].
extension DoctorInfoDtoPatterns on DoctorInfoDto {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _DoctorInfoDto value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _DoctorInfoDto() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _DoctorInfoDto value)  $default,){
final _that = this;
switch (_that) {
case _DoctorInfoDto():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _DoctorInfoDto value)?  $default,){
final _that = this;
switch (_that) {
case _DoctorInfoDto() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function(@JsonKey(name: 'id')  String doctorId, @JsonKey(name: 'user', fromJson: _userFromJson)  String doctorName,  String specialization)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _DoctorInfoDto() when $default != null:
return $default(_that.doctorId,_that.doctorName,_that.specialization);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function(@JsonKey(name: 'id')  String doctorId, @JsonKey(name: 'user', fromJson: _userFromJson)  String doctorName,  String specialization)  $default,) {final _that = this;
switch (_that) {
case _DoctorInfoDto():
return $default(_that.doctorId,_that.doctorName,_that.specialization);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function(@JsonKey(name: 'id')  String doctorId, @JsonKey(name: 'user', fromJson: _userFromJson)  String doctorName,  String specialization)?  $default,) {final _that = this;
switch (_that) {
case _DoctorInfoDto() when $default != null:
return $default(_that.doctorId,_that.doctorName,_that.specialization);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _DoctorInfoDto implements DoctorInfoDto {
  const _DoctorInfoDto({@JsonKey(name: 'id') required this.doctorId, @JsonKey(name: 'user', fromJson: _userFromJson) required this.doctorName, required this.specialization});
  factory _DoctorInfoDto.fromJson(Map<String, dynamic> json) => _$DoctorInfoDtoFromJson(json);

@override@JsonKey(name: 'id') final  String doctorId;
@override@JsonKey(name: 'user', fromJson: _userFromJson) final  String doctorName;
@override final  String specialization;

/// Create a copy of DoctorInfoDto
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$DoctorInfoDtoCopyWith<_DoctorInfoDto> get copyWith => __$DoctorInfoDtoCopyWithImpl<_DoctorInfoDto>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$DoctorInfoDtoToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _DoctorInfoDto&&(identical(other.doctorId, doctorId) || other.doctorId == doctorId)&&(identical(other.doctorName, doctorName) || other.doctorName == doctorName)&&(identical(other.specialization, specialization) || other.specialization == specialization));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,doctorId,doctorName,specialization);

@override
String toString() {
  return 'DoctorInfoDto(doctorId: $doctorId, doctorName: $doctorName, specialization: $specialization)';
}


}

/// @nodoc
abstract mixin class _$DoctorInfoDtoCopyWith<$Res> implements $DoctorInfoDtoCopyWith<$Res> {
  factory _$DoctorInfoDtoCopyWith(_DoctorInfoDto value, $Res Function(_DoctorInfoDto) _then) = __$DoctorInfoDtoCopyWithImpl;
@override @useResult
$Res call({
@JsonKey(name: 'id') String doctorId,@JsonKey(name: 'user', fromJson: _userFromJson) String doctorName, String specialization
});




}
/// @nodoc
class __$DoctorInfoDtoCopyWithImpl<$Res>
    implements _$DoctorInfoDtoCopyWith<$Res> {
  __$DoctorInfoDtoCopyWithImpl(this._self, this._then);

  final _DoctorInfoDto _self;
  final $Res Function(_DoctorInfoDto) _then;

/// Create a copy of DoctorInfoDto
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? doctorId = null,Object? doctorName = null,Object? specialization = null,}) {
  return _then(_DoctorInfoDto(
doctorId: null == doctorId ? _self.doctorId : doctorId // ignore: cast_nullable_to_non_nullable
as String,doctorName: null == doctorName ? _self.doctorName : doctorName // ignore: cast_nullable_to_non_nullable
as String,specialization: null == specialization ? _self.specialization : specialization // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}

// dart format on
