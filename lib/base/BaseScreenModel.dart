import 'package:flutter/material.dart';

abstract class BaseScreenModel extends ChangeNotifier {
  bool _isLoading = false;
  bool _isError = false;
  String? _errorMessage;

  BaseScreenModel();

  bool get isLoading => _isLoading;

  bool get isError => _isError;

  String? get errorMessage => _errorMessage;

  set isLoading(bool value) {
    _isLoading = value;
    notifyListeners();
  }

  set isError(bool value) {
    _isError = value;
    notifyListeners();
  }

  Future<void> onInitialization();

  Future<void> initialize() async {
    _isLoading = true;
    notifyListeners();

    try {
      await onInitialization();
      _isError = false;
      _errorMessage = null;
    } catch (e) {
      _isError = true;
      _errorMessage = e.toString();
      rethrow;
    } finally {
      _isLoading = false;
      notifyListeners();
    }
  }
}
