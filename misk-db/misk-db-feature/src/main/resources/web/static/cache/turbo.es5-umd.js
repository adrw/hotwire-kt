/*
Turbo 7.0.0-beta.3
Copyright © 2021 Basecamp, LLC
 */
(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? factory(exports) :
    typeof define === 'function' && define.amd ? define(['exports'], factory) :
    (global = typeof globalThis !== 'undefined' ? globalThis : global || self, factory(global.Turbo = {}));
}(this, (function (exports) { 'use strict';

    (function () {
        if (window.Reflect === undefined || window.customElements === undefined ||
            window.customElements.polyfillWrapFlushCallback) {
            return;
        }
        var BuiltInHTMLElement = HTMLElement;
        var wrapperForTheName = {
            'HTMLElement': function HTMLElement() {
                return Reflect.construct(BuiltInHTMLElement, [], this.constructor);
            }
        };
        window.HTMLElement =
            wrapperForTheName['HTMLElement'];
        HTMLElement.prototype = BuiltInHTMLElement.prototype;
        HTMLElement.prototype.constructor = HTMLElement;
        Object.setPrototypeOf(HTMLElement, BuiltInHTMLElement);
    })();

    var submittersByForm = new WeakMap;
    function findSubmitterFromClickTarget(target) {
        var element = target instanceof Element ? target : target instanceof Node ? target.parentElement : null;
        var candidate = element ? element.closest("input, button") : null;
        return (candidate === null || candidate === void 0 ? void 0 : candidate.type) == "submit" ? candidate : null;
    }
    function clickCaptured(event) {
        var submitter = findSubmitterFromClickTarget(event.target);
        if (submitter && submitter.form) {
            submittersByForm.set(submitter.form, submitter);
        }
    }
    (function () {
        if ("SubmitEvent" in window)
            return;
        addEventListener("click", clickCaptured, true);
        Object.defineProperty(Event.prototype, "submitter", {
            get: function () {
                if (this.type == "submit" && this.target instanceof HTMLFormElement) {
                    return submittersByForm.get(this.target);
                }
            }
        });
    })();

    /*! *****************************************************************************
    Copyright (c) Microsoft Corporation.

    Permission to use, copy, modify, and/or distribute this software for any
    purpose with or without fee is hereby granted.

    THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
    REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
    AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
    INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
    LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
    OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
    PERFORMANCE OF THIS SOFTWARE.
    ***************************************************************************** */
    /* global Reflect, Promise */

    var extendStatics = function(d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (Object.prototype.hasOwnProperty.call(b, p)) d[p] = b[p]; };
        return extendStatics(d, b);
    };

    function __extends(d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    }

    var __assign = function() {
        __assign = Object.assign || function __assign(t) {
            for (var s, i = 1, n = arguments.length; i < n; i++) {
                s = arguments[i];
                for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p)) t[p] = s[p];
            }
            return t;
        };
        return __assign.apply(this, arguments);
    };

    function __awaiter(thisArg, _arguments, P, generator) {
        function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
        return new (P || (P = Promise))(function (resolve, reject) {
            function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
            function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
            function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
            step((generator = generator.apply(thisArg, _arguments || [])).next());
        });
    }

    function __generator(thisArg, body) {
        var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
        return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
        function verb(n) { return function (v) { return step([n, v]); }; }
        function step(op) {
            if (f) throw new TypeError("Generator is already executing.");
            while (_) try {
                if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
                if (y = 0, t) op = [op[0] & 2, t.value];
                switch (op[0]) {
                    case 0: case 1: t = op; break;
                    case 4: _.label++; return { value: op[1], done: false };
                    case 5: _.label++; y = op[1]; op = [0]; continue;
                    case 7: op = _.ops.pop(); _.trys.pop(); continue;
                    default:
                        if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                        if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                        if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                        if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                        if (t[2]) _.ops.pop();
                        _.trys.pop(); continue;
                }
                op = body.call(thisArg, _);
            } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
            if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
        }
    }

    function __values(o) {
        var s = typeof Symbol === "function" && Symbol.iterator, m = s && o[s], i = 0;
        if (m) return m.call(o);
        if (o && typeof o.length === "number") return {
            next: function () {
                if (o && i >= o.length) o = void 0;
                return { value: o && o[i++], done: !o };
            }
        };
        throw new TypeError(s ? "Object is not iterable." : "Symbol.iterator is not defined.");
    }

    function __read(o, n) {
        var m = typeof Symbol === "function" && o[Symbol.iterator];
        if (!m) return o;
        var i = m.call(o), r, ar = [], e;
        try {
            while ((n === void 0 || n-- > 0) && !(r = i.next()).done) ar.push(r.value);
        }
        catch (error) { e = { error: error }; }
        finally {
            try {
                if (r && !r.done && (m = i["return"])) m.call(i);
            }
            finally { if (e) throw e.error; }
        }
        return ar;
    }

    function __spread() {
        for (var ar = [], i = 0; i < arguments.length; i++)
            ar = ar.concat(__read(arguments[i]));
        return ar;
    }

    function __makeTemplateObject(cooked, raw) {
        if (Object.defineProperty) { Object.defineProperty(cooked, "raw", { value: raw }); } else { cooked.raw = raw; }
        return cooked;
    }

    var FrameLoadingStyle;
    (function (FrameLoadingStyle) {
        FrameLoadingStyle["eager"] = "eager";
        FrameLoadingStyle["lazy"] = "lazy";
    })(FrameLoadingStyle || (FrameLoadingStyle = {}));
    var FrameElement = (function (_super) {
        __extends(FrameElement, _super);
        function FrameElement() {
            var _this = _super.call(this) || this;
            _this.loaded = Promise.resolve();
            _this.delegate = new FrameElement.delegateConstructor(_this);
            return _this;
        }
        Object.defineProperty(FrameElement, "observedAttributes", {
            get: function () {
                return ["loading", "src"];
            },
            enumerable: false,
            configurable: true
        });
        FrameElement.prototype.connectedCallback = function () {
            this.delegate.connect();
        };
        FrameElement.prototype.disconnectedCallback = function () {
            this.delegate.disconnect();
        };
        FrameElement.prototype.attributeChangedCallback = function (name) {
            if (name == "loading") {
                this.delegate.loadingStyleChanged();
            }
            else if (name == "src") {
                this.delegate.sourceURLChanged();
            }
        };
        Object.defineProperty(FrameElement.prototype, "src", {
            get: function () {
                return this.getAttribute("src");
            },
            set: function (value) {
                if (value) {
                    this.setAttribute("src", value);
                }
                else {
                    this.removeAttribute("src");
                }
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameElement.prototype, "loading", {
            get: function () {
                return frameLoadingStyleFromString(this.getAttribute("loading") || "");
            },
            set: function (value) {
                if (value) {
                    this.setAttribute("loading", value);
                }
                else {
                    this.removeAttribute("loading");
                }
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameElement.prototype, "disabled", {
            get: function () {
                return this.hasAttribute("disabled");
            },
            set: function (value) {
                if (value) {
                    this.setAttribute("disabled", "");
                }
                else {
                    this.removeAttribute("disabled");
                }
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameElement.prototype, "autoscroll", {
            get: function () {
                return this.hasAttribute("autoscroll");
            },
            set: function (value) {
                if (value) {
                    this.setAttribute("autoscroll", "");
                }
                else {
                    this.removeAttribute("autoscroll");
                }
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameElement.prototype, "complete", {
            get: function () {
                return !this.delegate.isLoading;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameElement.prototype, "isActive", {
            get: function () {
                return this.ownerDocument === document && !this.isPreview;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameElement.prototype, "isPreview", {
            get: function () {
                var _a, _b;
                return (_b = (_a = this.ownerDocument) === null || _a === void 0 ? void 0 : _a.documentElement) === null || _b === void 0 ? void 0 : _b.hasAttribute("data-turbo-preview");
            },
            enumerable: false,
            configurable: true
        });
        return FrameElement;
    }(HTMLElement));
    function frameLoadingStyleFromString(style) {
        switch (style.toLowerCase()) {
            case "lazy": return FrameLoadingStyle.lazy;
            default: return FrameLoadingStyle.eager;
        }
    }

    var Location = (function () {
        function Location(url) {
            var linkWithAnchor = document.createElement("a");
            linkWithAnchor.href = url;
            this.absoluteURL = linkWithAnchor.href;
            var anchorLength = linkWithAnchor.hash.length;
            if (anchorLength < 2) {
                this.requestURL = this.absoluteURL;
            }
            else {
                this.requestURL = this.absoluteURL.slice(0, -anchorLength);
                this.anchor = linkWithAnchor.hash.slice(1);
            }
        }
        Object.defineProperty(Location, "currentLocation", {
            get: function () {
                return this.wrap(window.location.toString());
            },
            enumerable: false,
            configurable: true
        });
        Location.wrap = function (locatable) {
            if (typeof locatable == "string") {
                return new this(locatable);
            }
            else if (locatable != null) {
                return locatable;
            }
        };
        Location.prototype.getOrigin = function () {
            return this.absoluteURL.split("/", 3).join("/");
        };
        Location.prototype.getPath = function () {
            return (this.requestURL.match(/\/\/[^/]*(\/[^?;]*)/) || [])[1] || "/";
        };
        Location.prototype.getPathComponents = function () {
            return this.getPath().split("/").slice(1);
        };
        Location.prototype.getLastPathComponent = function () {
            return this.getPathComponents().slice(-1)[0];
        };
        Location.prototype.getExtension = function () {
            return (this.getLastPathComponent().match(/\.[^.]*$/) || [])[0] || "";
        };
        Location.prototype.isHTML = function () {
            return !!this.getExtension().match(/^(?:|\.(?:htm|html|xhtml))$/);
        };
        Location.prototype.isPrefixedBy = function (location) {
            var prefixURL = getPrefixURL(location);
            return this.isEqualTo(location) || stringStartsWith(this.absoluteURL, prefixURL);
        };
        Location.prototype.isEqualTo = function (location) {
            return location && this.absoluteURL === location.absoluteURL;
        };
        Location.prototype.toCacheKey = function () {
            return this.requestURL;
        };
        Location.prototype.toJSON = function () {
            return this.absoluteURL;
        };
        Location.prototype.toString = function () {
            return this.absoluteURL;
        };
        Location.prototype.valueOf = function () {
            return this.absoluteURL;
        };
        return Location;
    }());
    function getPrefixURL(location) {
        return addTrailingSlash(location.getOrigin() + location.getPath());
    }
    function addTrailingSlash(url) {
        return stringEndsWith(url, "/") ? url : url + "/";
    }
    function stringStartsWith(string, prefix) {
        return string.slice(0, prefix.length) === prefix;
    }
    function stringEndsWith(string, suffix) {
        return string.slice(-suffix.length) === suffix;
    }

    var FetchResponse = (function () {
        function FetchResponse(response) {
            this.response = response;
        }
        Object.defineProperty(FetchResponse.prototype, "succeeded", {
            get: function () {
                return this.response.ok;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "failed", {
            get: function () {
                return !this.succeeded;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "clientError", {
            get: function () {
                return this.statusCode >= 400 && this.statusCode <= 499;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "serverError", {
            get: function () {
                return this.statusCode >= 500 && this.statusCode <= 599;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "redirected", {
            get: function () {
                return this.response.redirected;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "location", {
            get: function () {
                return Location.wrap(this.response.url);
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "isHTML", {
            get: function () {
                return this.contentType && this.contentType.match(/^(?:text\/([^\s;,]+\b)?html|application\/xhtml\+xml)\b/);
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "statusCode", {
            get: function () {
                return this.response.status;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "contentType", {
            get: function () {
                return this.header("Content-Type");
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "responseText", {
            get: function () {
                return this.response.text();
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchResponse.prototype, "responseHTML", {
            get: function () {
                if (this.isHTML) {
                    return this.response.text();
                }
                else {
                    return Promise.resolve(undefined);
                }
            },
            enumerable: false,
            configurable: true
        });
        FetchResponse.prototype.header = function (name) {
            return this.response.headers.get(name);
        };
        return FetchResponse;
    }());

    function dispatch(eventName, _a) {
        var _b = _a === void 0 ? {} : _a, target = _b.target, cancelable = _b.cancelable, detail = _b.detail;
        var event = new CustomEvent(eventName, { cancelable: cancelable, bubbles: true, detail: detail });
        void (target || document.documentElement).dispatchEvent(event);
        return event;
    }
    function nextAnimationFrame() {
        return new Promise(function (resolve) { return requestAnimationFrame(function () { return resolve(); }); });
    }
    function nextMicrotask() {
        return Promise.resolve();
    }
    function unindent(strings) {
        var values = [];
        for (var _i = 1; _i < arguments.length; _i++) {
            values[_i - 1] = arguments[_i];
        }
        var lines = interpolate(strings, values).replace(/^\n/, "").split("\n");
        var match = lines[0].match(/^\s+/);
        var indent = match ? match[0].length : 0;
        return lines.map(function (line) { return line.slice(indent); }).join("\n");
    }
    function interpolate(strings, values) {
        return strings.reduce(function (result, string, i) {
            var value = values[i] == undefined ? "" : values[i];
            return result + string + value;
        }, "");
    }
    function uuid() {
        return Array.apply(null, { length: 36 }).map(function (_, i) {
            if (i == 8 || i == 13 || i == 18 || i == 23) {
                return "-";
            }
            else if (i == 14) {
                return "4";
            }
            else if (i == 19) {
                return (Math.floor(Math.random() * 4) + 8).toString(16);
            }
            else {
                return Math.floor(Math.random() * 15).toString(16);
            }
        }).join("");
    }

    var FetchMethod;
    (function (FetchMethod) {
        FetchMethod[FetchMethod["get"] = 0] = "get";
        FetchMethod[FetchMethod["post"] = 1] = "post";
        FetchMethod[FetchMethod["put"] = 2] = "put";
        FetchMethod[FetchMethod["patch"] = 3] = "patch";
        FetchMethod[FetchMethod["delete"] = 4] = "delete";
    })(FetchMethod || (FetchMethod = {}));
    function fetchMethodFromString(method) {
        switch (method.toLowerCase()) {
            case "get": return FetchMethod.get;
            case "post": return FetchMethod.post;
            case "put": return FetchMethod.put;
            case "patch": return FetchMethod.patch;
            case "delete": return FetchMethod.delete;
        }
    }
    var FetchRequest = (function () {
        function FetchRequest(delegate, method, location, body) {
            this.abortController = new AbortController;
            this.delegate = delegate;
            this.method = method;
            this.location = location;
            this.body = body;
        }
        Object.defineProperty(FetchRequest.prototype, "url", {
            get: function () {
                var url = this.location.absoluteURL;
                var query = this.params.toString();
                if (this.isIdempotent && query.length) {
                    return [url, query].join(url.includes("?") ? "&" : "?");
                }
                else {
                    return url;
                }
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchRequest.prototype, "params", {
            get: function () {
                return this.entries.reduce(function (params, _a) {
                    var _b = __read(_a, 2), name = _b[0], value = _b[1];
                    params.append(name, value.toString());
                    return params;
                }, new URLSearchParams);
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchRequest.prototype, "entries", {
            get: function () {
                return this.body ? Array.from(this.body.entries()) : [];
            },
            enumerable: false,
            configurable: true
        });
        FetchRequest.prototype.cancel = function () {
            this.abortController.abort();
        };
        FetchRequest.prototype.perform = function () {
            return __awaiter(this, void 0, void 0, function () {
                var fetchOptions, response, error_1;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            fetchOptions = this.fetchOptions;
                            dispatch("turbo:before-fetch-request", { detail: { fetchOptions: fetchOptions } });
                            _a.label = 1;
                        case 1:
                            _a.trys.push([1, 4, 5, 6]);
                            this.delegate.requestStarted(this);
                            return [4, fetch(this.url, fetchOptions)];
                        case 2:
                            response = _a.sent();
                            return [4, this.receive(response)];
                        case 3: return [2, _a.sent()];
                        case 4:
                            error_1 = _a.sent();
                            this.delegate.requestErrored(this, error_1);
                            throw error_1;
                        case 5:
                            this.delegate.requestFinished(this);
                            return [7];
                        case 6: return [2];
                    }
                });
            });
        };
        FetchRequest.prototype.receive = function (response) {
            return __awaiter(this, void 0, void 0, function () {
                var fetchResponse, event;
                return __generator(this, function (_a) {
                    fetchResponse = new FetchResponse(response);
                    event = dispatch("turbo:before-fetch-response", { cancelable: true, detail: { fetchResponse: fetchResponse } });
                    if (event.defaultPrevented) {
                        this.delegate.requestPreventedHandlingResponse(this, fetchResponse);
                    }
                    else if (fetchResponse.succeeded) {
                        this.delegate.requestSucceededWithResponse(this, fetchResponse);
                    }
                    else {
                        this.delegate.requestFailedWithResponse(this, fetchResponse);
                    }
                    return [2, fetchResponse];
                });
            });
        };
        Object.defineProperty(FetchRequest.prototype, "fetchOptions", {
            get: function () {
                return {
                    method: FetchMethod[this.method].toUpperCase(),
                    credentials: "same-origin",
                    headers: this.headers,
                    redirect: "follow",
                    body: this.isIdempotent ? undefined : this.body,
                    signal: this.abortSignal
                };
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchRequest.prototype, "isIdempotent", {
            get: function () {
                return this.method == FetchMethod.get;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchRequest.prototype, "headers", {
            get: function () {
                return __assign({ "Accept": "text/html, application/xhtml+xml" }, this.additionalHeaders);
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchRequest.prototype, "additionalHeaders", {
            get: function () {
                if (typeof this.delegate.additionalHeadersForRequest == "function") {
                    return this.delegate.additionalHeadersForRequest(this);
                }
                else {
                    return {};
                }
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FetchRequest.prototype, "abortSignal", {
            get: function () {
                return this.abortController.signal;
            },
            enumerable: false,
            configurable: true
        });
        return FetchRequest;
    }());

    var AppearanceObserver = (function () {
        function AppearanceObserver(delegate, element) {
            var _this = this;
            this.started = false;
            this.intersect = function (entries) {
                var lastEntry = entries.slice(-1)[0];
                if (lastEntry === null || lastEntry === void 0 ? void 0 : lastEntry.isIntersecting) {
                    _this.delegate.elementAppearedInViewport(_this.element);
                }
            };
            this.delegate = delegate;
            this.element = element;
            this.intersectionObserver = new IntersectionObserver(this.intersect);
        }
        AppearanceObserver.prototype.start = function () {
            if (!this.started) {
                this.started = true;
                this.intersectionObserver.observe(this.element);
            }
        };
        AppearanceObserver.prototype.stop = function () {
            if (this.started) {
                this.started = false;
                this.intersectionObserver.unobserve(this.element);
            }
        };
        return AppearanceObserver;
    }());

    var FormSubmissionState;
    (function (FormSubmissionState) {
        FormSubmissionState[FormSubmissionState["initialized"] = 0] = "initialized";
        FormSubmissionState[FormSubmissionState["requesting"] = 1] = "requesting";
        FormSubmissionState[FormSubmissionState["waiting"] = 2] = "waiting";
        FormSubmissionState[FormSubmissionState["receiving"] = 3] = "receiving";
        FormSubmissionState[FormSubmissionState["stopping"] = 4] = "stopping";
        FormSubmissionState[FormSubmissionState["stopped"] = 5] = "stopped";
    })(FormSubmissionState || (FormSubmissionState = {}));
    var FormSubmission = (function () {
        function FormSubmission(delegate, formElement, submitter, mustRedirect) {
            if (mustRedirect === void 0) { mustRedirect = false; }
            this.state = FormSubmissionState.initialized;
            this.delegate = delegate;
            this.formElement = formElement;
            this.formData = buildFormData(formElement, submitter);
            this.submitter = submitter;
            this.fetchRequest = new FetchRequest(this, this.method, this.location, this.formData);
            this.mustRedirect = mustRedirect;
        }
        Object.defineProperty(FormSubmission.prototype, "method", {
            get: function () {
                var _a;
                var method = ((_a = this.submitter) === null || _a === void 0 ? void 0 : _a.getAttribute("formmethod")) || this.formElement.getAttribute("method") || "";
                return fetchMethodFromString(method.toLowerCase()) || FetchMethod.get;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FormSubmission.prototype, "action", {
            get: function () {
                var _a;
                return ((_a = this.submitter) === null || _a === void 0 ? void 0 : _a.getAttribute("formaction")) || this.formElement.action;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FormSubmission.prototype, "location", {
            get: function () {
                return Location.wrap(this.action);
            },
            enumerable: false,
            configurable: true
        });
        FormSubmission.prototype.start = function () {
            return __awaiter(this, void 0, void 0, function () {
                var initialized, requesting;
                return __generator(this, function (_a) {
                    initialized = FormSubmissionState.initialized, requesting = FormSubmissionState.requesting;
                    if (this.state == initialized) {
                        this.state = requesting;
                        return [2, this.fetchRequest.perform()];
                    }
                    return [2];
                });
            });
        };
        FormSubmission.prototype.stop = function () {
            var stopping = FormSubmissionState.stopping, stopped = FormSubmissionState.stopped;
            if (this.state != stopping && this.state != stopped) {
                this.state = stopping;
                this.fetchRequest.cancel();
                return true;
            }
        };
        FormSubmission.prototype.additionalHeadersForRequest = function (request) {
            var headers = {};
            if (this.method != FetchMethod.get) {
                var token = getCookieValue(getMetaContent("csrf-param")) || getMetaContent("csrf-token");
                if (token) {
                    headers["X-CSRF-Token"] = token;
                }
            }
            return headers;
        };
        FormSubmission.prototype.requestStarted = function (request) {
            this.state = FormSubmissionState.waiting;
            dispatch("turbo:submit-start", { target: this.formElement, detail: { formSubmission: this } });
            this.delegate.formSubmissionStarted(this);
        };
        FormSubmission.prototype.requestPreventedHandlingResponse = function (request, response) {
            this.result = { success: response.succeeded, fetchResponse: response };
        };
        FormSubmission.prototype.requestSucceededWithResponse = function (request, response) {
            if (response.clientError || response.serverError) {
                this.delegate.formSubmissionFailedWithResponse(this, response);
            }
            else if (this.requestMustRedirect(request) && responseSucceededWithoutRedirect(response)) {
                var error = new Error("Form responses must redirect to another location");
                this.delegate.formSubmissionErrored(this, error);
            }
            else {
                this.state = FormSubmissionState.receiving;
                this.result = { success: true, fetchResponse: response };
                this.delegate.formSubmissionSucceededWithResponse(this, response);
            }
        };
        FormSubmission.prototype.requestFailedWithResponse = function (request, response) {
            this.result = { success: false, fetchResponse: response };
            this.delegate.formSubmissionFailedWithResponse(this, response);
        };
        FormSubmission.prototype.requestErrored = function (request, error) {
            this.result = { success: false, error: error };
            this.delegate.formSubmissionErrored(this, error);
        };
        FormSubmission.prototype.requestFinished = function (request) {
            this.state = FormSubmissionState.stopped;
            dispatch("turbo:submit-end", { target: this.formElement, detail: __assign({ formSubmission: this }, this.result) });
            this.delegate.formSubmissionFinished(this);
        };
        FormSubmission.prototype.requestMustRedirect = function (request) {
            return !request.isIdempotent && this.mustRedirect;
        };
        return FormSubmission;
    }());
    function buildFormData(formElement, submitter) {
        var formData = new FormData(formElement);
        var name = submitter === null || submitter === void 0 ? void 0 : submitter.getAttribute("name");
        var value = submitter === null || submitter === void 0 ? void 0 : submitter.getAttribute("value");
        if (name && formData.get(name) != value) {
            formData.append(name, value || "");
        }
        return formData;
    }
    function getCookieValue(cookieName) {
        if (cookieName != null) {
            var cookies = document.cookie ? document.cookie.split("; ") : [];
            var cookie = cookies.find(function (cookie) { return cookie.startsWith(cookieName); });
            if (cookie) {
                var value = cookie.split("=").slice(1).join("=");
                return value ? decodeURIComponent(value) : undefined;
            }
        }
    }
    function getMetaContent(name) {
        var element = document.querySelector("meta[name=\"" + name + "\"]");
        return element && element.content;
    }
    function responseSucceededWithoutRedirect(response) {
        return response.statusCode == 200 && !response.redirected;
    }

    var FormInterceptor = (function () {
        function FormInterceptor(delegate, element) {
            var _this = this;
            this.submitBubbled = (function (event) {
                if (event.target instanceof HTMLFormElement) {
                    var form = event.target;
                    var submitter = event.submitter || undefined;
                    if (_this.delegate.shouldInterceptFormSubmission(form, submitter)) {
                        event.preventDefault();
                        event.stopImmediatePropagation();
                        _this.delegate.formSubmissionIntercepted(form, submitter);
                    }
                }
            });
            this.delegate = delegate;
            this.element = element;
        }
        FormInterceptor.prototype.start = function () {
            this.element.addEventListener("submit", this.submitBubbled);
        };
        FormInterceptor.prototype.stop = function () {
            this.element.removeEventListener("submit", this.submitBubbled);
        };
        return FormInterceptor;
    }());

    var LinkInterceptor = (function () {
        function LinkInterceptor(delegate, element) {
            var _this = this;
            this.clickBubbled = function (event) {
                if (_this.respondsToEventTarget(event.target)) {
                    _this.clickEvent = event;
                }
                else {
                    delete _this.clickEvent;
                }
            };
            this.linkClicked = (function (event) {
                if (_this.clickEvent && _this.respondsToEventTarget(event.target) && event.target instanceof Element) {
                    if (_this.delegate.shouldInterceptLinkClick(event.target, event.detail.url)) {
                        _this.clickEvent.preventDefault();
                        event.preventDefault();
                        _this.delegate.linkClickIntercepted(event.target, event.detail.url);
                    }
                }
                delete _this.clickEvent;
            });
            this.willVisit = function () {
                delete _this.clickEvent;
            };
            this.delegate = delegate;
            this.element = element;
        }
        LinkInterceptor.prototype.start = function () {
            this.element.addEventListener("click", this.clickBubbled);
            document.addEventListener("turbo:click", this.linkClicked);
            document.addEventListener("turbo:before-visit", this.willVisit);
        };
        LinkInterceptor.prototype.stop = function () {
            this.element.removeEventListener("click", this.clickBubbled);
            document.removeEventListener("turbo:click", this.linkClicked);
            document.removeEventListener("turbo:before-visit", this.willVisit);
        };
        LinkInterceptor.prototype.respondsToEventTarget = function (target) {
            var element = target instanceof Element
                ? target
                : target instanceof Node
                    ? target.parentElement
                    : null;
            return element && element.closest("turbo-frame, html") == this.element;
        };
        return LinkInterceptor;
    }());

    var FrameController = (function () {
        function FrameController(element) {
            this.resolveVisitPromise = function () { };
            this.element = element;
            this.appearanceObserver = new AppearanceObserver(this, this.element);
            this.linkInterceptor = new LinkInterceptor(this, this.element);
            this.formInterceptor = new FormInterceptor(this, this.element);
        }
        FrameController.prototype.connect = function () {
            if (this.loadingStyle == FrameLoadingStyle.lazy) {
                this.appearanceObserver.start();
            }
            this.linkInterceptor.start();
            this.formInterceptor.start();
        };
        FrameController.prototype.disconnect = function () {
            this.appearanceObserver.stop();
            this.linkInterceptor.stop();
            this.formInterceptor.stop();
        };
        FrameController.prototype.sourceURLChanged = function () {
            if (this.loadingStyle == FrameLoadingStyle.eager) {
                this.loadSourceURL();
            }
        };
        FrameController.prototype.loadingStyleChanged = function () {
            if (this.loadingStyle == FrameLoadingStyle.lazy) {
                this.appearanceObserver.start();
            }
            else {
                this.appearanceObserver.stop();
                this.loadSourceURL();
            }
        };
        FrameController.prototype.loadSourceURL = function () {
            return __awaiter(this, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            if (!(this.isActive && this.sourceURL && this.sourceURL != this.loadingURL)) return [3, 4];
                            _a.label = 1;
                        case 1:
                            _a.trys.push([1, , 3, 4]);
                            this.loadingURL = this.sourceURL;
                            this.element.loaded = this.visit(this.sourceURL);
                            this.appearanceObserver.stop();
                            return [4, this.element.loaded];
                        case 2:
                            _a.sent();
                            return [3, 4];
                        case 3:
                            delete this.loadingURL;
                            return [7];
                        case 4: return [2];
                    }
                });
            });
        };
        FrameController.prototype.loadResponse = function (response) {
            return __awaiter(this, void 0, void 0, function () {
                var fragment, _a, element;
                return __generator(this, function (_b) {
                    switch (_b.label) {
                        case 0:
                            _a = fragmentFromHTML;
                            return [4, response.responseHTML];
                        case 1:
                            fragment = _a.apply(void 0, [_b.sent()]);
                            if (!fragment) return [3, 5];
                            return [4, this.extractForeignFrameElement(fragment)];
                        case 2:
                            element = _b.sent();
                            return [4, nextAnimationFrame()];
                        case 3:
                            _b.sent();
                            this.loadFrameElement(element);
                            this.scrollFrameIntoView(element);
                            return [4, nextAnimationFrame()];
                        case 4:
                            _b.sent();
                            this.focusFirstAutofocusableElement();
                            _b.label = 5;
                        case 5: return [2];
                    }
                });
            });
        };
        FrameController.prototype.elementAppearedInViewport = function (element) {
            this.loadSourceURL();
        };
        FrameController.prototype.shouldInterceptLinkClick = function (element, url) {
            return this.shouldInterceptNavigation(element);
        };
        FrameController.prototype.linkClickIntercepted = function (element, url) {
            this.navigateFrame(element, url);
        };
        FrameController.prototype.shouldInterceptFormSubmission = function (element) {
            return this.shouldInterceptNavigation(element);
        };
        FrameController.prototype.formSubmissionIntercepted = function (element, submitter) {
            if (this.formSubmission) {
                this.formSubmission.stop();
            }
            this.formSubmission = new FormSubmission(this, element, submitter);
            if (this.formSubmission.fetchRequest.isIdempotent) {
                this.navigateFrame(element, this.formSubmission.fetchRequest.url);
            }
            else {
                this.formSubmission.start();
            }
        };
        FrameController.prototype.additionalHeadersForRequest = function (request) {
            return { "Turbo-Frame": this.id };
        };
        FrameController.prototype.requestStarted = function (request) {
            this.element.setAttribute("busy", "");
        };
        FrameController.prototype.requestPreventedHandlingResponse = function (request, response) {
            this.resolveVisitPromise();
        };
        FrameController.prototype.requestSucceededWithResponse = function (request, response) {
            return __awaiter(this, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0: return [4, this.loadResponse(response)];
                        case 1:
                            _a.sent();
                            this.resolveVisitPromise();
                            return [2];
                    }
                });
            });
        };
        FrameController.prototype.requestFailedWithResponse = function (request, response) {
            console.error(response);
            this.resolveVisitPromise();
        };
        FrameController.prototype.requestErrored = function (request, error) {
            console.error(error);
            this.resolveVisitPromise();
        };
        FrameController.prototype.requestFinished = function (request) {
            this.element.removeAttribute("busy");
        };
        FrameController.prototype.formSubmissionStarted = function (formSubmission) {
        };
        FrameController.prototype.formSubmissionSucceededWithResponse = function (formSubmission, response) {
            var frame = this.findFrameElement(formSubmission.formElement);
            frame.delegate.loadResponse(response);
        };
        FrameController.prototype.formSubmissionFailedWithResponse = function (formSubmission, fetchResponse) {
            this.element.delegate.loadResponse(fetchResponse);
        };
        FrameController.prototype.formSubmissionErrored = function (formSubmission, error) {
        };
        FrameController.prototype.formSubmissionFinished = function (formSubmission) {
        };
        FrameController.prototype.visit = function (url) {
            return __awaiter(this, void 0, void 0, function () {
                var location, request;
                var _this = this;
                return __generator(this, function (_a) {
                    location = Location.wrap(url);
                    request = new FetchRequest(this, FetchMethod.get, location);
                    return [2, new Promise(function (resolve) {
                            _this.resolveVisitPromise = function () {
                                _this.resolveVisitPromise = function () { };
                                resolve();
                            };
                            request.perform();
                        })];
                });
            });
        };
        FrameController.prototype.navigateFrame = function (element, url) {
            var frame = this.findFrameElement(element);
            frame.src = url;
        };
        FrameController.prototype.findFrameElement = function (element) {
            var _a;
            var id = element.getAttribute("data-turbo-frame");
            return (_a = getFrameElementById(id)) !== null && _a !== void 0 ? _a : this.element;
        };
        FrameController.prototype.extractForeignFrameElement = function (container) {
            return __awaiter(this, void 0, void 0, function () {
                var element, id;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            id = CSS.escape(this.id);
                            if (element = activateElement(container.querySelector("turbo-frame#" + id))) {
                                return [2, element];
                            }
                            if (!(element = activateElement(container.querySelector("turbo-frame[src][recurse~=" + id + "]")))) return [3, 3];
                            return [4, element.loaded];
                        case 1:
                            _a.sent();
                            return [4, this.extractForeignFrameElement(element)];
                        case 2: return [2, _a.sent()];
                        case 3:
                            console.error("Response has no matching <turbo-frame id=\"" + id + "\"> element");
                            return [2, new FrameElement()];
                    }
                });
            });
        };
        FrameController.prototype.loadFrameElement = function (frameElement) {
            var _a;
            var destinationRange = document.createRange();
            destinationRange.selectNodeContents(this.element);
            destinationRange.deleteContents();
            var sourceRange = (_a = frameElement.ownerDocument) === null || _a === void 0 ? void 0 : _a.createRange();
            if (sourceRange) {
                sourceRange.selectNodeContents(frameElement);
                this.element.appendChild(sourceRange.extractContents());
            }
        };
        FrameController.prototype.focusFirstAutofocusableElement = function () {
            var element = this.firstAutofocusableElement;
            if (element) {
                element.focus();
                return true;
            }
            return false;
        };
        FrameController.prototype.scrollFrameIntoView = function (frame) {
            if (this.element.autoscroll || frame.autoscroll) {
                var element = this.element.firstElementChild;
                var block = readScrollLogicalPosition(this.element.getAttribute("data-autoscroll-block"), "end");
                if (element) {
                    element.scrollIntoView({ block: block });
                    return true;
                }
            }
            return false;
        };
        FrameController.prototype.shouldInterceptNavigation = function (element) {
            var id = element.getAttribute("data-turbo-frame") || this.element.getAttribute("target");
            if (!this.enabled || id == "_top") {
                return false;
            }
            if (id) {
                var frameElement_1 = getFrameElementById(id);
                if (frameElement_1) {
                    return !frameElement_1.disabled;
                }
            }
            return true;
        };
        Object.defineProperty(FrameController.prototype, "firstAutofocusableElement", {
            get: function () {
                var element = this.element.querySelector("[autofocus]");
                return element instanceof HTMLElement ? element : null;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameController.prototype, "id", {
            get: function () {
                return this.element.id;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameController.prototype, "enabled", {
            get: function () {
                return !this.element.disabled;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameController.prototype, "sourceURL", {
            get: function () {
                return this.element.src;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameController.prototype, "loadingStyle", {
            get: function () {
                return this.element.loading;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameController.prototype, "isLoading", {
            get: function () {
                return this.formSubmission !== undefined || this.loadingURL !== undefined;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(FrameController.prototype, "isActive", {
            get: function () {
                return this.element.isActive;
            },
            enumerable: false,
            configurable: true
        });
        return FrameController;
    }());
    function getFrameElementById(id) {
        if (id != null) {
            var element = document.getElementById(id);
            if (element instanceof FrameElement) {
                return element;
            }
        }
    }
    function readScrollLogicalPosition(value, defaultValue) {
        if (value == "end" || value == "start" || value == "center" || value == "nearest") {
            return value;
        }
        else {
            return defaultValue;
        }
    }
    function fragmentFromHTML(html) {
        if (html) {
            var foreignDocument = document.implementation.createHTMLDocument();
            return foreignDocument.createRange().createContextualFragment(html);
        }
    }
    function activateElement(element) {
        if (element && element.ownerDocument !== document) {
            element = document.importNode(element, true);
        }
        if (element instanceof FrameElement) {
            return element;
        }
    }

    var StreamActions = {
        append: function () {
            var _a;
            (_a = this.targetElement) === null || _a === void 0 ? void 0 : _a.append(this.templateContent);
        },
        prepend: function () {
            var _a;
            (_a = this.targetElement) === null || _a === void 0 ? void 0 : _a.prepend(this.templateContent);
        },
        remove: function () {
            var _a;
            (_a = this.targetElement) === null || _a === void 0 ? void 0 : _a.remove();
        },
        replace: function () {
            var _a;
            (_a = this.targetElement) === null || _a === void 0 ? void 0 : _a.replaceWith(this.templateContent);
        },
        update: function () {
            if (this.targetElement) {
                this.targetElement.innerHTML = "";
                this.targetElement.append(this.templateContent);
            }
        }
    };

    var StreamElement = (function (_super) {
        __extends(StreamElement, _super);
        function StreamElement() {
            return _super !== null && _super.apply(this, arguments) || this;
        }
        StreamElement.prototype.connectedCallback = function () {
            return __awaiter(this, void 0, void 0, function () {
                var error_1;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            _a.trys.push([0, 2, 3, 4]);
                            return [4, this.render()];
                        case 1:
                            _a.sent();
                            return [3, 4];
                        case 2:
                            error_1 = _a.sent();
                            console.error(error_1);
                            return [3, 4];
                        case 3:
                            this.disconnect();
                            return [7];
                        case 4: return [2];
                    }
                });
            });
        };
        StreamElement.prototype.render = function () {
            var _a;
            return __awaiter(this, void 0, void 0, function () {
                var _this = this;
                return __generator(this, function (_b) {
                    return [2, (_a = this.renderPromise) !== null && _a !== void 0 ? _a : (this.renderPromise = (function () { return __awaiter(_this, void 0, void 0, function () {
                            return __generator(this, function (_a) {
                                switch (_a.label) {
                                    case 0:
                                        if (!this.dispatchEvent(this.beforeRenderEvent)) return [3, 2];
                                        return [4, nextAnimationFrame()];
                                    case 1:
                                        _a.sent();
                                        this.performAction();
                                        _a.label = 2;
                                    case 2: return [2];
                                }
                            });
                        }); })())];
                });
            });
        };
        StreamElement.prototype.disconnect = function () {
            try {
                this.remove();
            }
            catch (_a) { }
        };
        Object.defineProperty(StreamElement.prototype, "performAction", {
            get: function () {
                if (this.action) {
                    var actionFunction = StreamActions[this.action];
                    if (actionFunction) {
                        return actionFunction;
                    }
                    this.raise("unknown action");
                }
                this.raise("action attribute is missing");
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamElement.prototype, "targetElement", {
            get: function () {
                var _a;
                if (this.target) {
                    return (_a = this.ownerDocument) === null || _a === void 0 ? void 0 : _a.getElementById(this.target);
                }
                this.raise("target attribute is missing");
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamElement.prototype, "templateContent", {
            get: function () {
                return this.templateElement.content;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamElement.prototype, "templateElement", {
            get: function () {
                if (this.firstElementChild instanceof HTMLTemplateElement) {
                    return this.firstElementChild;
                }
                this.raise("first child element must be a <template> element");
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamElement.prototype, "action", {
            get: function () {
                return this.getAttribute("action");
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamElement.prototype, "target", {
            get: function () {
                return this.getAttribute("target");
            },
            enumerable: false,
            configurable: true
        });
        StreamElement.prototype.raise = function (message) {
            throw new Error(this.description + ": " + message);
        };
        Object.defineProperty(StreamElement.prototype, "description", {
            get: function () {
                var _a, _b;
                return (_b = ((_a = this.outerHTML.match(/<[^>]+>/)) !== null && _a !== void 0 ? _a : [])[0]) !== null && _b !== void 0 ? _b : "<turbo-stream>";
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamElement.prototype, "beforeRenderEvent", {
            get: function () {
                return new CustomEvent("turbo:before-stream-render", { bubbles: true, cancelable: true });
            },
            enumerable: false,
            configurable: true
        });
        return StreamElement;
    }(HTMLElement));

    FrameElement.delegateConstructor = FrameController;
    customElements.define("turbo-frame", FrameElement);
    customElements.define("turbo-stream", StreamElement);

    (function () {
        var element = document.currentScript;
        if (!element)
            return;
        if (element.hasAttribute("data-turbo-suppress-warning"))
            return;
        while (element = element.parentElement) {
            if (element == document.body) {
                return console.warn(unindent(templateObject_1 || (templateObject_1 = __makeTemplateObject(["\n        You are loading Turbo from a <script> element inside the <body> element. This is probably not what you meant to do!\n\n        Load your application\u2019s JavaScript bundle inside the <head> element instead. <script> elements in <body> are evaluated with each page change.\n\n        For more information, see: https://turbo.hotwire.dev/handbook/building#working-with-script-elements\n\n        \u2014\u2014\n        Suppress this warning by adding a \"data-turbo-suppress-warning\" attribute to: %s\n      "], ["\n        You are loading Turbo from a <script> element inside the <body> element. This is probably not what you meant to do!\n\n        Load your application\u2019s JavaScript bundle inside the <head> element instead. <script> elements in <body> are evaluated with each page change.\n\n        For more information, see: https://turbo.hotwire.dev/handbook/building#working-with-script-elements\n\n        \u2014\u2014\n        Suppress this warning by adding a \"data-turbo-suppress-warning\" attribute to: %s\n      "]))), element.outerHTML);
            }
        }
    })();
    var templateObject_1;

    var ProgressBar = (function () {
        function ProgressBar() {
            var _this = this;
            this.hiding = false;
            this.value = 0;
            this.visible = false;
            this.trickle = function () {
                _this.setValue(_this.value + Math.random() / 100);
            };
            this.stylesheetElement = this.createStylesheetElement();
            this.progressElement = this.createProgressElement();
            this.installStylesheetElement();
            this.setValue(0);
        }
        Object.defineProperty(ProgressBar, "defaultCSS", {
            get: function () {
                return unindent(templateObject_1$1 || (templateObject_1$1 = __makeTemplateObject(["\n      .turbo-progress-bar {\n        position: fixed;\n        display: block;\n        top: 0;\n        left: 0;\n        height: 3px;\n        background: #0076ff;\n        z-index: 9999;\n        transition:\n          width ", "ms ease-out,\n          opacity ", "ms ", "ms ease-in;\n        transform: translate3d(0, 0, 0);\n      }\n    "], ["\n      .turbo-progress-bar {\n        position: fixed;\n        display: block;\n        top: 0;\n        left: 0;\n        height: 3px;\n        background: #0076ff;\n        z-index: 9999;\n        transition:\n          width ", "ms ease-out,\n          opacity ", "ms ", "ms ease-in;\n        transform: translate3d(0, 0, 0);\n      }\n    "])), ProgressBar.animationDuration, ProgressBar.animationDuration / 2, ProgressBar.animationDuration / 2);
            },
            enumerable: false,
            configurable: true
        });
        ProgressBar.prototype.show = function () {
            if (!this.visible) {
                this.visible = true;
                this.installProgressElement();
                this.startTrickling();
            }
        };
        ProgressBar.prototype.hide = function () {
            var _this = this;
            if (this.visible && !this.hiding) {
                this.hiding = true;
                this.fadeProgressElement(function () {
                    _this.uninstallProgressElement();
                    _this.stopTrickling();
                    _this.visible = false;
                    _this.hiding = false;
                });
            }
        };
        ProgressBar.prototype.setValue = function (value) {
            this.value = value;
            this.refresh();
        };
        ProgressBar.prototype.installStylesheetElement = function () {
            document.head.insertBefore(this.stylesheetElement, document.head.firstChild);
        };
        ProgressBar.prototype.installProgressElement = function () {
            this.progressElement.style.width = "0";
            this.progressElement.style.opacity = "1";
            document.documentElement.insertBefore(this.progressElement, document.body);
            this.refresh();
        };
        ProgressBar.prototype.fadeProgressElement = function (callback) {
            this.progressElement.style.opacity = "0";
            setTimeout(callback, ProgressBar.animationDuration * 1.5);
        };
        ProgressBar.prototype.uninstallProgressElement = function () {
            if (this.progressElement.parentNode) {
                document.documentElement.removeChild(this.progressElement);
            }
        };
        ProgressBar.prototype.startTrickling = function () {
            if (!this.trickleInterval) {
                this.trickleInterval = window.setInterval(this.trickle, ProgressBar.animationDuration);
            }
        };
        ProgressBar.prototype.stopTrickling = function () {
            window.clearInterval(this.trickleInterval);
            delete this.trickleInterval;
        };
        ProgressBar.prototype.refresh = function () {
            var _this = this;
            requestAnimationFrame(function () {
                _this.progressElement.style.width = 10 + (_this.value * 90) + "%";
            });
        };
        ProgressBar.prototype.createStylesheetElement = function () {
            var element = document.createElement("style");
            element.type = "text/css";
            element.textContent = ProgressBar.defaultCSS;
            return element;
        };
        ProgressBar.prototype.createProgressElement = function () {
            var element = document.createElement("div");
            element.className = "turbo-progress-bar";
            return element;
        };
        ProgressBar.animationDuration = 300;
        return ProgressBar;
    }());
    var templateObject_1$1;

    var HeadDetails = (function () {
        function HeadDetails(children) {
            this.detailsByOuterHTML = children.reduce(function (result, element) {
                var _a;
                var outerHTML = element.outerHTML;
                var details = outerHTML in result
                    ? result[outerHTML]
                    : {
                        type: elementType(element),
                        tracked: elementIsTracked(element),
                        elements: []
                    };
                return __assign(__assign({}, result), (_a = {}, _a[outerHTML] = __assign(__assign({}, details), { elements: __spread(details.elements, [element]) }), _a));
            }, {});
        }
        HeadDetails.fromHeadElement = function (headElement) {
            var children = headElement ? __spread(headElement.children) : [];
            return new this(children);
        };
        HeadDetails.prototype.getTrackedElementSignature = function () {
            var _this = this;
            return Object.keys(this.detailsByOuterHTML)
                .filter(function (outerHTML) { return _this.detailsByOuterHTML[outerHTML].tracked; })
                .join("");
        };
        HeadDetails.prototype.getScriptElementsNotInDetails = function (headDetails) {
            return this.getElementsMatchingTypeNotInDetails("script", headDetails);
        };
        HeadDetails.prototype.getStylesheetElementsNotInDetails = function (headDetails) {
            return this.getElementsMatchingTypeNotInDetails("stylesheet", headDetails);
        };
        HeadDetails.prototype.getElementsMatchingTypeNotInDetails = function (matchedType, headDetails) {
            var _this = this;
            return Object.keys(this.detailsByOuterHTML)
                .filter(function (outerHTML) { return !(outerHTML in headDetails.detailsByOuterHTML); })
                .map(function (outerHTML) { return _this.detailsByOuterHTML[outerHTML]; })
                .filter(function (_a) {
                var type = _a.type;
                return type == matchedType;
            })
                .map(function (_a) {
                var _b = __read(_a.elements, 1), element = _b[0];
                return element;
            });
        };
        HeadDetails.prototype.getProvisionalElements = function () {
            var _this = this;
            return Object.keys(this.detailsByOuterHTML).reduce(function (result, outerHTML) {
                var _a = _this.detailsByOuterHTML[outerHTML], type = _a.type, tracked = _a.tracked, elements = _a.elements;
                if (type == null && !tracked) {
                    return __spread(result, elements);
                }
                else if (elements.length > 1) {
                    return __spread(result, elements.slice(1));
                }
                else {
                    return result;
                }
            }, []);
        };
        HeadDetails.prototype.getMetaValue = function (name) {
            var element = this.findMetaElementByName(name);
            return element
                ? element.getAttribute("content")
                : null;
        };
        HeadDetails.prototype.findMetaElementByName = function (name) {
            var _this = this;
            return Object.keys(this.detailsByOuterHTML).reduce(function (result, outerHTML) {
                var _a = __read(_this.detailsByOuterHTML[outerHTML].elements, 1), element = _a[0];
                return elementIsMetaElementWithName(element, name) ? element : result;
            }, undefined);
        };
        return HeadDetails;
    }());
    function elementType(element) {
        if (elementIsScript(element)) {
            return "script";
        }
        else if (elementIsStylesheet(element)) {
            return "stylesheet";
        }
    }
    function elementIsTracked(element) {
        return element.getAttribute("data-turbo-track") == "reload";
    }
    function elementIsScript(element) {
        var tagName = element.tagName.toLowerCase();
        return tagName == "script";
    }
    function elementIsStylesheet(element) {
        var tagName = element.tagName.toLowerCase();
        return tagName == "style" || (tagName == "link" && element.getAttribute("rel") == "stylesheet");
    }
    function elementIsMetaElementWithName(element, name) {
        var tagName = element.tagName.toLowerCase();
        return tagName == "meta" && element.getAttribute("name") == name;
    }

    var Snapshot = (function () {
        function Snapshot(headDetails, bodyElement) {
            this.headDetails = headDetails;
            this.bodyElement = bodyElement;
        }
        Snapshot.wrap = function (value) {
            if (value instanceof this) {
                return value;
            }
            else if (typeof value == "string") {
                return this.fromHTMLString(value);
            }
            else {
                return this.fromHTMLElement(value);
            }
        };
        Snapshot.fromHTMLString = function (html) {
            var documentElement = new DOMParser().parseFromString(html, "text/html").documentElement;
            return this.fromHTMLElement(documentElement);
        };
        Snapshot.fromHTMLElement = function (htmlElement) {
            var headElement = htmlElement.querySelector("head");
            var bodyElement = htmlElement.querySelector("body") || document.createElement("body");
            var headDetails = HeadDetails.fromHeadElement(headElement);
            return new this(headDetails, bodyElement);
        };
        Snapshot.prototype.clone = function () {
            var bodyElement = Snapshot.fromHTMLString(this.bodyElement.outerHTML).bodyElement;
            return new Snapshot(this.headDetails, bodyElement);
        };
        Snapshot.prototype.getRootLocation = function () {
            var root = this.getSetting("root", "/");
            return new Location(root);
        };
        Snapshot.prototype.getCacheControlValue = function () {
            return this.getSetting("cache-control");
        };
        Snapshot.prototype.getElementForAnchor = function (anchor) {
            try {
                return this.bodyElement.querySelector("[id='" + anchor + "'], a[name='" + anchor + "']");
            }
            catch (_a) {
                return null;
            }
        };
        Snapshot.prototype.getPermanentElements = function () {
            return __spread(this.bodyElement.querySelectorAll("[id][data-turbo-permanent]"));
        };
        Snapshot.prototype.getPermanentElementById = function (id) {
            return this.bodyElement.querySelector("#" + id + "[data-turbo-permanent]");
        };
        Snapshot.prototype.getPermanentElementsPresentInSnapshot = function (snapshot) {
            return this.getPermanentElements().filter(function (_a) {
                var id = _a.id;
                return snapshot.getPermanentElementById(id);
            });
        };
        Snapshot.prototype.findFirstAutofocusableElement = function () {
            return this.bodyElement.querySelector("[autofocus]");
        };
        Snapshot.prototype.hasAnchor = function (anchor) {
            return this.getElementForAnchor(anchor) != null;
        };
        Snapshot.prototype.isPreviewable = function () {
            return this.getCacheControlValue() != "no-preview";
        };
        Snapshot.prototype.isCacheable = function () {
            return this.getCacheControlValue() != "no-cache";
        };
        Snapshot.prototype.isVisitable = function () {
            return this.getSetting("visit-control") != "reload";
        };
        Snapshot.prototype.getSetting = function (name, defaultValue) {
            var value = this.headDetails.getMetaValue("turbo-" + name);
            return value == null ? defaultValue : value;
        };
        return Snapshot;
    }());

    var TimingMetric;
    (function (TimingMetric) {
        TimingMetric["visitStart"] = "visitStart";
        TimingMetric["requestStart"] = "requestStart";
        TimingMetric["requestEnd"] = "requestEnd";
        TimingMetric["visitEnd"] = "visitEnd";
    })(TimingMetric || (TimingMetric = {}));
    var VisitState;
    (function (VisitState) {
        VisitState["initialized"] = "initialized";
        VisitState["started"] = "started";
        VisitState["canceled"] = "canceled";
        VisitState["failed"] = "failed";
        VisitState["completed"] = "completed";
    })(VisitState || (VisitState = {}));
    var defaultOptions = {
        action: "advance",
        historyChanged: false
    };
    var SystemStatusCode;
    (function (SystemStatusCode) {
        SystemStatusCode[SystemStatusCode["networkFailure"] = 0] = "networkFailure";
        SystemStatusCode[SystemStatusCode["timeoutFailure"] = -1] = "timeoutFailure";
        SystemStatusCode[SystemStatusCode["contentTypeMismatch"] = -2] = "contentTypeMismatch";
    })(SystemStatusCode || (SystemStatusCode = {}));
    var Visit = (function () {
        function Visit(delegate, location, restorationIdentifier, options) {
            var _this = this;
            if (options === void 0) { options = {}; }
            this.identifier = uuid();
            this.timingMetrics = {};
            this.followedRedirect = false;
            this.historyChanged = false;
            this.scrolled = false;
            this.snapshotCached = false;
            this.state = VisitState.initialized;
            this.performScroll = function () {
                if (!_this.scrolled) {
                    if (_this.action == "restore") {
                        _this.scrollToRestoredPosition() || _this.scrollToTop();
                    }
                    else {
                        _this.scrollToAnchor() || _this.scrollToTop();
                    }
                    _this.scrolled = true;
                }
            };
            this.delegate = delegate;
            this.location = location;
            this.restorationIdentifier = restorationIdentifier || uuid();
            var _a = __assign(__assign({}, defaultOptions), options), action = _a.action, historyChanged = _a.historyChanged, referrer = _a.referrer, snapshotHTML = _a.snapshotHTML, response = _a.response;
            this.action = action;
            this.historyChanged = historyChanged;
            this.referrer = referrer;
            this.snapshotHTML = snapshotHTML;
            this.response = response;
        }
        Object.defineProperty(Visit.prototype, "adapter", {
            get: function () {
                return this.delegate.adapter;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(Visit.prototype, "view", {
            get: function () {
                return this.delegate.view;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(Visit.prototype, "history", {
            get: function () {
                return this.delegate.history;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(Visit.prototype, "restorationData", {
            get: function () {
                return this.history.getRestorationDataForIdentifier(this.restorationIdentifier);
            },
            enumerable: false,
            configurable: true
        });
        Visit.prototype.start = function () {
            if (this.state == VisitState.initialized) {
                this.recordTimingMetric(TimingMetric.visitStart);
                this.state = VisitState.started;
                this.adapter.visitStarted(this);
                this.delegate.visitStarted(this);
            }
        };
        Visit.prototype.cancel = function () {
            if (this.state == VisitState.started) {
                if (this.request) {
                    this.request.cancel();
                }
                this.cancelRender();
                this.state = VisitState.canceled;
            }
        };
        Visit.prototype.complete = function () {
            if (this.state == VisitState.started) {
                this.recordTimingMetric(TimingMetric.visitEnd);
                this.state = VisitState.completed;
                this.adapter.visitCompleted(this);
                this.delegate.visitCompleted(this);
            }
        };
        Visit.prototype.fail = function () {
            if (this.state == VisitState.started) {
                this.state = VisitState.failed;
                this.adapter.visitFailed(this);
            }
        };
        Visit.prototype.changeHistory = function () {
            if (!this.historyChanged) {
                var actionForHistory = this.location.isEqualTo(this.referrer) ? "replace" : this.action;
                var method = this.getHistoryMethodForAction(actionForHistory);
                this.history.update(method, this.location, this.restorationIdentifier);
                this.historyChanged = true;
            }
        };
        Visit.prototype.issueRequest = function () {
            if (this.hasPreloadedResponse()) {
                this.simulateRequest();
            }
            else if (this.shouldIssueRequest() && !this.request) {
                this.request = new FetchRequest(this, FetchMethod.get, this.location);
                this.request.perform();
            }
        };
        Visit.prototype.simulateRequest = function () {
            if (this.response) {
                this.startRequest();
                this.recordResponse();
                this.finishRequest();
            }
        };
        Visit.prototype.startRequest = function () {
            this.recordTimingMetric(TimingMetric.requestStart);
            this.adapter.visitRequestStarted(this);
        };
        Visit.prototype.recordResponse = function (response) {
            if (response === void 0) { response = this.response; }
            this.response = response;
            if (response) {
                var statusCode = response.statusCode;
                if (isSuccessful(statusCode)) {
                    this.adapter.visitRequestCompleted(this);
                }
                else {
                    this.adapter.visitRequestFailedWithStatusCode(this, statusCode);
                }
            }
        };
        Visit.prototype.finishRequest = function () {
            this.recordTimingMetric(TimingMetric.requestEnd);
            this.adapter.visitRequestFinished(this);
        };
        Visit.prototype.loadResponse = function () {
            var _this = this;
            if (this.response) {
                var _a = this.response, statusCode_1 = _a.statusCode, responseHTML_1 = _a.responseHTML;
                this.render(function () {
                    _this.cacheSnapshot();
                    if (isSuccessful(statusCode_1) && responseHTML_1 != null) {
                        _this.view.render({ snapshot: Snapshot.fromHTMLString(responseHTML_1) }, _this.performScroll);
                        _this.adapter.visitRendered(_this);
                        _this.complete();
                    }
                    else {
                        _this.view.render({ error: responseHTML_1 }, _this.performScroll);
                        _this.adapter.visitRendered(_this);
                        _this.fail();
                    }
                });
            }
        };
        Visit.prototype.getCachedSnapshot = function () {
            var snapshot = this.view.getCachedSnapshotForLocation(this.location) || this.getPreloadedSnapshot();
            if (snapshot && (!this.location.anchor || snapshot.hasAnchor(this.location.anchor))) {
                if (this.action == "restore" || snapshot.isPreviewable()) {
                    return snapshot;
                }
            }
        };
        Visit.prototype.getPreloadedSnapshot = function () {
            if (this.snapshotHTML) {
                return Snapshot.wrap(this.snapshotHTML);
            }
        };
        Visit.prototype.hasCachedSnapshot = function () {
            return this.getCachedSnapshot() != null;
        };
        Visit.prototype.loadCachedSnapshot = function () {
            var _this = this;
            var snapshot = this.getCachedSnapshot();
            if (snapshot) {
                var isPreview_1 = this.shouldIssueRequest();
                this.render(function () {
                    _this.cacheSnapshot();
                    _this.view.render({ snapshot: snapshot, isPreview: isPreview_1 }, _this.performScroll);
                    _this.adapter.visitRendered(_this);
                    if (!isPreview_1) {
                        _this.complete();
                    }
                });
            }
        };
        Visit.prototype.followRedirect = function () {
            if (this.redirectedToLocation && !this.followedRedirect) {
                this.location = this.redirectedToLocation;
                this.history.replace(this.redirectedToLocation, this.restorationIdentifier);
                this.followedRedirect = true;
            }
        };
        Visit.prototype.requestStarted = function () {
            this.startRequest();
        };
        Visit.prototype.requestPreventedHandlingResponse = function (request, response) {
        };
        Visit.prototype.requestSucceededWithResponse = function (request, response) {
            return __awaiter(this, void 0, void 0, function () {
                var responseHTML;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0: return [4, response.responseHTML];
                        case 1:
                            responseHTML = _a.sent();
                            if (responseHTML == undefined) {
                                this.recordResponse({ statusCode: SystemStatusCode.contentTypeMismatch });
                            }
                            else {
                                this.redirectedToLocation = response.redirected ? response.location : undefined;
                                this.recordResponse({ statusCode: response.statusCode, responseHTML: responseHTML });
                            }
                            return [2];
                    }
                });
            });
        };
        Visit.prototype.requestFailedWithResponse = function (request, response) {
            return __awaiter(this, void 0, void 0, function () {
                var responseHTML;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0: return [4, response.responseHTML];
                        case 1:
                            responseHTML = _a.sent();
                            if (responseHTML == undefined) {
                                this.recordResponse({ statusCode: SystemStatusCode.contentTypeMismatch });
                            }
                            else {
                                this.recordResponse({ statusCode: response.statusCode, responseHTML: responseHTML });
                            }
                            return [2];
                    }
                });
            });
        };
        Visit.prototype.requestErrored = function (request, error) {
            this.recordResponse({ statusCode: SystemStatusCode.networkFailure });
        };
        Visit.prototype.requestFinished = function () {
            this.finishRequest();
        };
        Visit.prototype.scrollToRestoredPosition = function () {
            var scrollPosition = this.restorationData.scrollPosition;
            if (scrollPosition) {
                this.view.scrollToPosition(scrollPosition);
                return true;
            }
        };
        Visit.prototype.scrollToAnchor = function () {
            if (this.location.anchor != null) {
                this.view.scrollToAnchor(this.location.anchor);
                return true;
            }
        };
        Visit.prototype.scrollToTop = function () {
            this.view.scrollToPosition({ x: 0, y: 0 });
        };
        Visit.prototype.recordTimingMetric = function (metric) {
            this.timingMetrics[metric] = new Date().getTime();
        };
        Visit.prototype.getTimingMetrics = function () {
            return __assign({}, this.timingMetrics);
        };
        Visit.prototype.getHistoryMethodForAction = function (action) {
            switch (action) {
                case "replace": return history.replaceState;
                case "advance":
                case "restore": return history.pushState;
            }
        };
        Visit.prototype.hasPreloadedResponse = function () {
            return typeof this.response == "object";
        };
        Visit.prototype.shouldIssueRequest = function () {
            return this.action == "restore"
                ? !this.hasCachedSnapshot()
                : true;
        };
        Visit.prototype.cacheSnapshot = function () {
            if (!this.snapshotCached) {
                this.view.cacheSnapshot();
                this.snapshotCached = true;
            }
        };
        Visit.prototype.render = function (callback) {
            var _this = this;
            this.cancelRender();
            this.frame = requestAnimationFrame(function () {
                delete _this.frame;
                callback.call(_this);
            });
        };
        Visit.prototype.cancelRender = function () {
            if (this.frame) {
                cancelAnimationFrame(this.frame);
                delete this.frame;
            }
        };
        return Visit;
    }());
    function isSuccessful(statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    var BrowserAdapter = (function () {
        function BrowserAdapter(session) {
            var _this = this;
            this.progressBar = new ProgressBar;
            this.showProgressBar = function () {
                _this.progressBar.show();
            };
            this.session = session;
        }
        BrowserAdapter.prototype.visitProposedToLocation = function (location, options) {
            this.navigator.startVisit(location, uuid(), options);
        };
        BrowserAdapter.prototype.visitStarted = function (visit) {
            visit.issueRequest();
            visit.changeHistory();
            visit.loadCachedSnapshot();
        };
        BrowserAdapter.prototype.visitRequestStarted = function (visit) {
            this.progressBar.setValue(0);
            if (visit.hasCachedSnapshot() || visit.action != "restore") {
                this.showProgressBarAfterDelay();
            }
            else {
                this.showProgressBar();
            }
        };
        BrowserAdapter.prototype.visitRequestCompleted = function (visit) {
            visit.loadResponse();
        };
        BrowserAdapter.prototype.visitRequestFailedWithStatusCode = function (visit, statusCode) {
            switch (statusCode) {
                case SystemStatusCode.networkFailure:
                case SystemStatusCode.timeoutFailure:
                case SystemStatusCode.contentTypeMismatch:
                    return this.reload();
                default:
                    return visit.loadResponse();
            }
        };
        BrowserAdapter.prototype.visitRequestFinished = function (visit) {
            this.progressBar.setValue(1);
            this.hideProgressBar();
        };
        BrowserAdapter.prototype.visitCompleted = function (visit) {
            visit.followRedirect();
        };
        BrowserAdapter.prototype.pageInvalidated = function () {
            this.reload();
        };
        BrowserAdapter.prototype.visitFailed = function (visit) {
        };
        BrowserAdapter.prototype.visitRendered = function (visit) {
        };
        BrowserAdapter.prototype.showProgressBarAfterDelay = function () {
            this.progressBarTimeout = window.setTimeout(this.showProgressBar, this.session.progressBarDelay);
        };
        BrowserAdapter.prototype.hideProgressBar = function () {
            this.progressBar.hide();
            if (this.progressBarTimeout != null) {
                window.clearTimeout(this.progressBarTimeout);
                delete this.progressBarTimeout;
            }
        };
        BrowserAdapter.prototype.reload = function () {
            window.location.reload();
        };
        Object.defineProperty(BrowserAdapter.prototype, "navigator", {
            get: function () {
                return this.session.navigator;
            },
            enumerable: false,
            configurable: true
        });
        return BrowserAdapter;
    }());

    var FormSubmitObserver = (function () {
        function FormSubmitObserver(delegate) {
            var _this = this;
            this.started = false;
            this.submitCaptured = function () {
                removeEventListener("submit", _this.submitBubbled, false);
                addEventListener("submit", _this.submitBubbled, false);
            };
            this.submitBubbled = (function (event) {
                if (!event.defaultPrevented) {
                    var form = event.target instanceof HTMLFormElement ? event.target : undefined;
                    var submitter = event.submitter || undefined;
                    if (form) {
                        var method = (submitter === null || submitter === void 0 ? void 0 : submitter.getAttribute("formmethod")) || form.method;
                        if (method != "dialog" && _this.delegate.willSubmitForm(form, submitter)) {
                            event.preventDefault();
                            _this.delegate.formSubmitted(form, submitter);
                        }
                    }
                }
            });
            this.delegate = delegate;
        }
        FormSubmitObserver.prototype.start = function () {
            if (!this.started) {
                addEventListener("submit", this.submitCaptured, true);
                this.started = true;
            }
        };
        FormSubmitObserver.prototype.stop = function () {
            if (this.started) {
                removeEventListener("submit", this.submitCaptured, true);
                this.started = false;
            }
        };
        return FormSubmitObserver;
    }());

    var FrameRedirector = (function () {
        function FrameRedirector(element) {
            this.element = element;
            this.linkInterceptor = new LinkInterceptor(this, element);
            this.formInterceptor = new FormInterceptor(this, element);
        }
        FrameRedirector.prototype.start = function () {
            this.linkInterceptor.start();
            this.formInterceptor.start();
        };
        FrameRedirector.prototype.stop = function () {
            this.linkInterceptor.stop();
            this.formInterceptor.stop();
        };
        FrameRedirector.prototype.shouldInterceptLinkClick = function (element, url) {
            return this.shouldRedirect(element);
        };
        FrameRedirector.prototype.linkClickIntercepted = function (element, url) {
            var frame = this.findFrameElement(element);
            if (frame) {
                frame.src = url;
            }
        };
        FrameRedirector.prototype.shouldInterceptFormSubmission = function (element, submitter) {
            return this.shouldRedirect(element, submitter);
        };
        FrameRedirector.prototype.formSubmissionIntercepted = function (element, submitter) {
            var frame = this.findFrameElement(element);
            if (frame) {
                frame.delegate.formSubmissionIntercepted(element, submitter);
            }
        };
        FrameRedirector.prototype.shouldRedirect = function (element, submitter) {
            var frame = this.findFrameElement(element);
            return frame ? frame != element.closest("turbo-frame") : false;
        };
        FrameRedirector.prototype.findFrameElement = function (element) {
            var id = element.getAttribute("data-turbo-frame");
            if (id && id != "_top") {
                var frame = this.element.querySelector("#" + id + ":not([disabled])");
                if (frame instanceof FrameElement) {
                    return frame;
                }
            }
        };
        return FrameRedirector;
    }());

    var History = (function () {
        function History(delegate) {
            var _this = this;
            this.restorationIdentifier = uuid();
            this.restorationData = {};
            this.started = false;
            this.pageLoaded = false;
            this.onPopState = function (event) {
                if (_this.shouldHandlePopState()) {
                    var turbo = (event.state || {}).turbo;
                    if (turbo) {
                        var location_1 = Location.currentLocation;
                        _this.location = location_1;
                        var restorationIdentifier = turbo.restorationIdentifier;
                        _this.restorationIdentifier = restorationIdentifier;
                        _this.delegate.historyPoppedToLocationWithRestorationIdentifier(location_1, restorationIdentifier);
                    }
                }
            };
            this.onPageLoad = function (event) { return __awaiter(_this, void 0, void 0, function () {
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0: return [4, nextMicrotask()];
                        case 1:
                            _a.sent();
                            this.pageLoaded = true;
                            return [2];
                    }
                });
            }); };
            this.delegate = delegate;
        }
        History.prototype.start = function () {
            if (!this.started) {
                addEventListener("popstate", this.onPopState, false);
                addEventListener("load", this.onPageLoad, false);
                this.started = true;
                this.replace(Location.currentLocation);
            }
        };
        History.prototype.stop = function () {
            if (this.started) {
                removeEventListener("popstate", this.onPopState, false);
                removeEventListener("load", this.onPageLoad, false);
                this.started = false;
            }
        };
        History.prototype.push = function (location, restorationIdentifier) {
            this.update(history.pushState, location, restorationIdentifier);
        };
        History.prototype.replace = function (location, restorationIdentifier) {
            this.update(history.replaceState, location, restorationIdentifier);
        };
        History.prototype.update = function (method, location, restorationIdentifier) {
            if (restorationIdentifier === void 0) { restorationIdentifier = uuid(); }
            var state = { turbo: { restorationIdentifier: restorationIdentifier } };
            method.call(history, state, "", location.absoluteURL);
            this.location = location;
            this.restorationIdentifier = restorationIdentifier;
        };
        History.prototype.getRestorationDataForIdentifier = function (restorationIdentifier) {
            return this.restorationData[restorationIdentifier] || {};
        };
        History.prototype.updateRestorationData = function (additionalData) {
            var restorationIdentifier = this.restorationIdentifier;
            var restorationData = this.restorationData[restorationIdentifier];
            this.restorationData[restorationIdentifier] = __assign(__assign({}, restorationData), additionalData);
        };
        History.prototype.assumeControlOfScrollRestoration = function () {
            var _a;
            if (!this.previousScrollRestoration) {
                this.previousScrollRestoration = (_a = history.scrollRestoration) !== null && _a !== void 0 ? _a : "auto";
                history.scrollRestoration = "manual";
            }
        };
        History.prototype.relinquishControlOfScrollRestoration = function () {
            if (this.previousScrollRestoration) {
                history.scrollRestoration = this.previousScrollRestoration;
                delete this.previousScrollRestoration;
            }
        };
        History.prototype.shouldHandlePopState = function () {
            return this.pageIsLoaded();
        };
        History.prototype.pageIsLoaded = function () {
            return this.pageLoaded || document.readyState == "complete";
        };
        return History;
    }());

    var LinkClickObserver = (function () {
        function LinkClickObserver(delegate) {
            var _this = this;
            this.started = false;
            this.clickCaptured = function () {
                removeEventListener("click", _this.clickBubbled, false);
                addEventListener("click", _this.clickBubbled, false);
            };
            this.clickBubbled = function (event) {
                if (_this.clickEventIsSignificant(event)) {
                    var link = _this.findLinkFromClickTarget(event.target);
                    if (link) {
                        var location_1 = _this.getLocationForLink(link);
                        if (_this.delegate.willFollowLinkToLocation(link, location_1)) {
                            event.preventDefault();
                            _this.delegate.followedLinkToLocation(link, location_1);
                        }
                    }
                }
            };
            this.delegate = delegate;
        }
        LinkClickObserver.prototype.start = function () {
            if (!this.started) {
                addEventListener("click", this.clickCaptured, true);
                this.started = true;
            }
        };
        LinkClickObserver.prototype.stop = function () {
            if (this.started) {
                removeEventListener("click", this.clickCaptured, true);
                this.started = false;
            }
        };
        LinkClickObserver.prototype.clickEventIsSignificant = function (event) {
            return !((event.target && event.target.isContentEditable)
                || event.defaultPrevented
                || event.which > 1
                || event.altKey
                || event.ctrlKey
                || event.metaKey
                || event.shiftKey);
        };
        LinkClickObserver.prototype.findLinkFromClickTarget = function (target) {
            if (target instanceof Element) {
                return target.closest("a[href]:not([target^=_]):not([download])");
            }
        };
        LinkClickObserver.prototype.getLocationForLink = function (link) {
            return new Location(link.getAttribute("href") || "");
        };
        return LinkClickObserver;
    }());

    var Navigator = (function () {
        function Navigator(delegate) {
            this.delegate = delegate;
        }
        Navigator.prototype.proposeVisit = function (location, options) {
            if (options === void 0) { options = {}; }
            if (this.delegate.allowsVisitingLocation(location)) {
                this.delegate.visitProposedToLocation(location, options);
            }
        };
        Navigator.prototype.startVisit = function (location, restorationIdentifier, options) {
            if (options === void 0) { options = {}; }
            this.stop();
            this.currentVisit = new Visit(this, Location.wrap(location), restorationIdentifier, __assign({ referrer: this.location }, options));
            this.currentVisit.start();
        };
        Navigator.prototype.submitForm = function (form, submitter) {
            this.stop();
            this.formSubmission = new FormSubmission(this, form, submitter, true);
            this.formSubmission.start();
        };
        Navigator.prototype.stop = function () {
            if (this.formSubmission) {
                this.formSubmission.stop();
                delete this.formSubmission;
            }
            if (this.currentVisit) {
                this.currentVisit.cancel();
                delete this.currentVisit;
            }
        };
        Object.defineProperty(Navigator.prototype, "adapter", {
            get: function () {
                return this.delegate.adapter;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(Navigator.prototype, "view", {
            get: function () {
                return this.delegate.view;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(Navigator.prototype, "history", {
            get: function () {
                return this.delegate.history;
            },
            enumerable: false,
            configurable: true
        });
        Navigator.prototype.formSubmissionStarted = function (formSubmission) {
        };
        Navigator.prototype.formSubmissionSucceededWithResponse = function (formSubmission, fetchResponse) {
            return __awaiter(this, void 0, void 0, function () {
                var responseHTML, statusCode, visitOptions;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            if (!(formSubmission == this.formSubmission)) return [3, 2];
                            return [4, fetchResponse.responseHTML];
                        case 1:
                            responseHTML = _a.sent();
                            if (responseHTML) {
                                if (formSubmission.method != FetchMethod.get) {
                                    this.view.clearSnapshotCache();
                                }
                                statusCode = fetchResponse.statusCode;
                                visitOptions = { response: { statusCode: statusCode, responseHTML: responseHTML } };
                                this.proposeVisit(fetchResponse.location, visitOptions);
                            }
                            _a.label = 2;
                        case 2: return [2];
                    }
                });
            });
        };
        Navigator.prototype.formSubmissionFailedWithResponse = function (formSubmission, fetchResponse) {
            return __awaiter(this, void 0, void 0, function () {
                var responseHTML, snapshot;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0: return [4, fetchResponse.responseHTML];
                        case 1:
                            responseHTML = _a.sent();
                            if (responseHTML) {
                                snapshot = Snapshot.fromHTMLString(responseHTML);
                                this.view.render({ snapshot: snapshot }, function () { });
                                this.view.clearSnapshotCache();
                            }
                            return [2];
                    }
                });
            });
        };
        Navigator.prototype.formSubmissionErrored = function (formSubmission, error) {
        };
        Navigator.prototype.formSubmissionFinished = function (formSubmission) {
        };
        Navigator.prototype.visitStarted = function (visit) {
            this.delegate.visitStarted(visit);
        };
        Navigator.prototype.visitCompleted = function (visit) {
            this.delegate.visitCompleted(visit);
        };
        Object.defineProperty(Navigator.prototype, "location", {
            get: function () {
                return this.history.location;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(Navigator.prototype, "restorationIdentifier", {
            get: function () {
                return this.history.restorationIdentifier;
            },
            enumerable: false,
            configurable: true
        });
        return Navigator;
    }());

    var PageStage;
    (function (PageStage) {
        PageStage[PageStage["initial"] = 0] = "initial";
        PageStage[PageStage["loading"] = 1] = "loading";
        PageStage[PageStage["interactive"] = 2] = "interactive";
        PageStage[PageStage["complete"] = 3] = "complete";
    })(PageStage || (PageStage = {}));
    var PageObserver = (function () {
        function PageObserver(delegate) {
            var _this = this;
            this.stage = PageStage.initial;
            this.started = false;
            this.interpretReadyState = function () {
                var readyState = _this.readyState;
                if (readyState == "interactive") {
                    _this.pageIsInteractive();
                }
                else if (readyState == "complete") {
                    _this.pageIsComplete();
                }
            };
            this.pageWillUnload = function () {
                _this.delegate.pageWillUnload();
            };
            this.delegate = delegate;
        }
        PageObserver.prototype.start = function () {
            if (!this.started) {
                if (this.stage == PageStage.initial) {
                    this.stage = PageStage.loading;
                }
                document.addEventListener("readystatechange", this.interpretReadyState, false);
                addEventListener("pagehide", this.pageWillUnload, false);
                this.started = true;
            }
        };
        PageObserver.prototype.stop = function () {
            if (this.started) {
                document.removeEventListener("readystatechange", this.interpretReadyState, false);
                removeEventListener("pagehide", this.pageWillUnload, false);
                this.started = false;
            }
        };
        PageObserver.prototype.pageIsInteractive = function () {
            if (this.stage == PageStage.loading) {
                this.stage = PageStage.interactive;
                this.delegate.pageBecameInteractive();
            }
        };
        PageObserver.prototype.pageIsComplete = function () {
            this.pageIsInteractive();
            if (this.stage == PageStage.interactive) {
                this.stage = PageStage.complete;
                this.delegate.pageLoaded();
            }
        };
        Object.defineProperty(PageObserver.prototype, "readyState", {
            get: function () {
                return document.readyState;
            },
            enumerable: false,
            configurable: true
        });
        return PageObserver;
    }());

    var ScrollObserver = (function () {
        function ScrollObserver(delegate) {
            var _this = this;
            this.started = false;
            this.onScroll = function () {
                _this.updatePosition({ x: window.pageXOffset, y: window.pageYOffset });
            };
            this.delegate = delegate;
        }
        ScrollObserver.prototype.start = function () {
            if (!this.started) {
                addEventListener("scroll", this.onScroll, false);
                this.onScroll();
                this.started = true;
            }
        };
        ScrollObserver.prototype.stop = function () {
            if (this.started) {
                removeEventListener("scroll", this.onScroll, false);
                this.started = false;
            }
        };
        ScrollObserver.prototype.updatePosition = function (position) {
            this.delegate.scrollPositionChanged(position);
        };
        return ScrollObserver;
    }());

    var StreamMessage = (function () {
        function StreamMessage(html) {
            this.templateElement = document.createElement("template");
            this.templateElement.innerHTML = html;
        }
        StreamMessage.wrap = function (message) {
            if (typeof message == "string") {
                return new this(message);
            }
            else {
                return message;
            }
        };
        Object.defineProperty(StreamMessage.prototype, "fragment", {
            get: function () {
                var e_1, _a;
                var fragment = document.createDocumentFragment();
                try {
                    for (var _b = __values(this.foreignElements), _c = _b.next(); !_c.done; _c = _b.next()) {
                        var element = _c.value;
                        fragment.appendChild(document.importNode(element, true));
                    }
                }
                catch (e_1_1) { e_1 = { error: e_1_1 }; }
                finally {
                    try {
                        if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                    }
                    finally { if (e_1) throw e_1.error; }
                }
                return fragment;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamMessage.prototype, "foreignElements", {
            get: function () {
                return this.templateChildren.reduce(function (streamElements, child) {
                    if (child.tagName.toLowerCase() == "turbo-stream") {
                        return __spread(streamElements, [child]);
                    }
                    else {
                        return streamElements;
                    }
                }, []);
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(StreamMessage.prototype, "templateChildren", {
            get: function () {
                return Array.from(this.templateElement.content.children);
            },
            enumerable: false,
            configurable: true
        });
        return StreamMessage;
    }());

    var StreamObserver = (function () {
        function StreamObserver(delegate) {
            var _this = this;
            this.sources = new Set;
            this.started = false;
            this.prepareFetchRequest = (function (event) {
                var _a;
                var fetchOptions = (_a = event.detail) === null || _a === void 0 ? void 0 : _a.fetchOptions;
                if (fetchOptions) {
                    var headers = fetchOptions.headers;
                    headers.Accept = ["text/vnd.turbo-stream.html", headers.Accept].join(", ");
                }
            });
            this.inspectFetchResponse = (function (event) {
                var response = fetchResponseFromEvent(event);
                if (response && fetchResponseIsStream(response)) {
                    event.preventDefault();
                    _this.receiveMessageResponse(response);
                }
            });
            this.receiveMessageEvent = function (event) {
                if (_this.started && typeof event.data == "string") {
                    _this.receiveMessageHTML(event.data);
                }
            };
            this.delegate = delegate;
        }
        StreamObserver.prototype.start = function () {
            if (!this.started) {
                this.started = true;
                addEventListener("turbo:before-fetch-request", this.prepareFetchRequest, true);
                addEventListener("turbo:before-fetch-response", this.inspectFetchResponse, false);
            }
        };
        StreamObserver.prototype.stop = function () {
            if (this.started) {
                this.started = false;
                removeEventListener("turbo:before-fetch-request", this.prepareFetchRequest, true);
                removeEventListener("turbo:before-fetch-response", this.inspectFetchResponse, false);
            }
        };
        StreamObserver.prototype.connectStreamSource = function (source) {
            if (!this.streamSourceIsConnected(source)) {
                this.sources.add(source);
                source.addEventListener("message", this.receiveMessageEvent, false);
            }
        };
        StreamObserver.prototype.disconnectStreamSource = function (source) {
            if (this.streamSourceIsConnected(source)) {
                this.sources.delete(source);
                source.removeEventListener("message", this.receiveMessageEvent, false);
            }
        };
        StreamObserver.prototype.streamSourceIsConnected = function (source) {
            return this.sources.has(source);
        };
        StreamObserver.prototype.receiveMessageResponse = function (response) {
            return __awaiter(this, void 0, void 0, function () {
                var html;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0: return [4, response.responseHTML];
                        case 1:
                            html = _a.sent();
                            if (html) {
                                this.receiveMessageHTML(html);
                            }
                            return [2];
                    }
                });
            });
        };
        StreamObserver.prototype.receiveMessageHTML = function (html) {
            this.delegate.receivedMessageFromStream(new StreamMessage(html));
        };
        return StreamObserver;
    }());
    function fetchResponseFromEvent(event) {
        var _a;
        var fetchResponse = (_a = event.detail) === null || _a === void 0 ? void 0 : _a.fetchResponse;
        if (fetchResponse instanceof FetchResponse) {
            return fetchResponse;
        }
    }
    function fetchResponseIsStream(response) {
        var _a;
        var contentType = (_a = response.contentType) !== null && _a !== void 0 ? _a : "";
        return /^text\/vnd\.turbo-stream\.html\b/.test(contentType);
    }

    function isAction(action) {
        return action == "advance" || action == "replace" || action == "restore";
    }

    var Renderer = (function () {
        function Renderer() {
        }
        Renderer.prototype.renderView = function (callback) {
            this.delegate.viewWillRender(this.newBody);
            callback();
            this.delegate.viewRendered(this.newBody);
        };
        Renderer.prototype.invalidateView = function () {
            this.delegate.viewInvalidated();
        };
        Renderer.prototype.createScriptElement = function (element) {
            if (element.getAttribute("data-turbo-eval") == "false") {
                return element;
            }
            else {
                var createdScriptElement = document.createElement("script");
                createdScriptElement.textContent = element.textContent;
                createdScriptElement.async = false;
                copyElementAttributes(createdScriptElement, element);
                return createdScriptElement;
            }
        };
        return Renderer;
    }());
    function copyElementAttributes(destinationElement, sourceElement) {
        var e_1, _a;
        try {
            for (var _b = __values(__spread(sourceElement.attributes)), _c = _b.next(); !_c.done; _c = _b.next()) {
                var _d = _c.value, name_1 = _d.name, value = _d.value;
                destinationElement.setAttribute(name_1, value);
            }
        }
        catch (e_1_1) { e_1 = { error: e_1_1 }; }
        finally {
            try {
                if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
            }
            finally { if (e_1) throw e_1.error; }
        }
    }

    var ErrorRenderer = (function (_super) {
        __extends(ErrorRenderer, _super);
        function ErrorRenderer(delegate, html) {
            var _this = _super.call(this) || this;
            _this.delegate = delegate;
            _this.htmlElement = (function () {
                var htmlElement = document.createElement("html");
                htmlElement.innerHTML = html;
                return htmlElement;
            })();
            _this.newHead = _this.htmlElement.querySelector("head") || document.createElement("head");
            _this.newBody = _this.htmlElement.querySelector("body") || document.createElement("body");
            return _this;
        }
        ErrorRenderer.render = function (delegate, callback, html) {
            return new this(delegate, html).render(callback);
        };
        ErrorRenderer.prototype.render = function (callback) {
            var _this = this;
            this.renderView(function () {
                _this.replaceHeadAndBody();
                _this.activateBodyScriptElements();
                callback();
            });
        };
        ErrorRenderer.prototype.replaceHeadAndBody = function () {
            var documentElement = document.documentElement, head = document.head, body = document.body;
            documentElement.replaceChild(this.newHead, head);
            documentElement.replaceChild(this.newBody, body);
        };
        ErrorRenderer.prototype.activateBodyScriptElements = function () {
            var e_1, _a;
            try {
                for (var _b = __values(this.getScriptElements()), _c = _b.next(); !_c.done; _c = _b.next()) {
                    var replaceableElement = _c.value;
                    var parentNode = replaceableElement.parentNode;
                    if (parentNode) {
                        var element = this.createScriptElement(replaceableElement);
                        parentNode.replaceChild(element, replaceableElement);
                    }
                }
            }
            catch (e_1_1) { e_1 = { error: e_1_1 }; }
            finally {
                try {
                    if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                }
                finally { if (e_1) throw e_1.error; }
            }
        };
        ErrorRenderer.prototype.getScriptElements = function () {
            return __spread(document.documentElement.querySelectorAll("script"));
        };
        return ErrorRenderer;
    }(Renderer));

    var SnapshotCache = (function () {
        function SnapshotCache(size) {
            this.keys = [];
            this.snapshots = {};
            this.size = size;
        }
        SnapshotCache.prototype.has = function (location) {
            return location.toCacheKey() in this.snapshots;
        };
        SnapshotCache.prototype.get = function (location) {
            if (this.has(location)) {
                var snapshot = this.read(location);
                this.touch(location);
                return snapshot;
            }
        };
        SnapshotCache.prototype.put = function (location, snapshot) {
            this.write(location, snapshot);
            this.touch(location);
            return snapshot;
        };
        SnapshotCache.prototype.clear = function () {
            this.snapshots = {};
        };
        SnapshotCache.prototype.read = function (location) {
            return this.snapshots[location.toCacheKey()];
        };
        SnapshotCache.prototype.write = function (location, snapshot) {
            this.snapshots[location.toCacheKey()] = snapshot;
        };
        SnapshotCache.prototype.touch = function (location) {
            var key = location.toCacheKey();
            var index = this.keys.indexOf(key);
            if (index > -1)
                this.keys.splice(index, 1);
            this.keys.unshift(key);
            this.trim();
        };
        SnapshotCache.prototype.trim = function () {
            var e_1, _a;
            try {
                for (var _b = __values(this.keys.splice(this.size)), _c = _b.next(); !_c.done; _c = _b.next()) {
                    var key = _c.value;
                    delete this.snapshots[key];
                }
            }
            catch (e_1_1) { e_1 = { error: e_1_1 }; }
            finally {
                try {
                    if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                }
                finally { if (e_1) throw e_1.error; }
            }
        };
        return SnapshotCache;
    }());

    var SnapshotRenderer = (function (_super) {
        __extends(SnapshotRenderer, _super);
        function SnapshotRenderer(delegate, currentSnapshot, newSnapshot, isPreview) {
            var _this = _super.call(this) || this;
            _this.delegate = delegate;
            _this.currentSnapshot = currentSnapshot;
            _this.currentHeadDetails = currentSnapshot.headDetails;
            _this.newSnapshot = newSnapshot;
            _this.newHeadDetails = newSnapshot.headDetails;
            _this.newBody = newSnapshot.bodyElement;
            _this.isPreview = isPreview;
            return _this;
        }
        SnapshotRenderer.render = function (delegate, callback, currentSnapshot, newSnapshot, isPreview) {
            return new this(delegate, currentSnapshot, newSnapshot, isPreview).render(callback);
        };
        SnapshotRenderer.prototype.render = function (callback) {
            var _this = this;
            if (this.shouldRender()) {
                this.mergeHead();
                this.renderView(function () {
                    _this.replaceBody();
                    if (!_this.isPreview) {
                        _this.focusFirstAutofocusableElement();
                    }
                    callback();
                });
            }
            else {
                this.invalidateView();
            }
        };
        SnapshotRenderer.prototype.mergeHead = function () {
            this.copyNewHeadStylesheetElements();
            this.copyNewHeadScriptElements();
            this.removeCurrentHeadProvisionalElements();
            this.copyNewHeadProvisionalElements();
        };
        SnapshotRenderer.prototype.replaceBody = function () {
            var placeholders = this.relocateCurrentBodyPermanentElements();
            this.activateNewBody();
            this.assignNewBody();
            this.replacePlaceholderElementsWithClonedPermanentElements(placeholders);
        };
        SnapshotRenderer.prototype.shouldRender = function () {
            return this.newSnapshot.isVisitable() && this.trackedElementsAreIdentical();
        };
        SnapshotRenderer.prototype.trackedElementsAreIdentical = function () {
            return this.currentHeadDetails.getTrackedElementSignature() == this.newHeadDetails.getTrackedElementSignature();
        };
        SnapshotRenderer.prototype.copyNewHeadStylesheetElements = function () {
            var e_1, _a;
            try {
                for (var _b = __values(this.getNewHeadStylesheetElements()), _c = _b.next(); !_c.done; _c = _b.next()) {
                    var element = _c.value;
                    document.head.appendChild(element);
                }
            }
            catch (e_1_1) { e_1 = { error: e_1_1 }; }
            finally {
                try {
                    if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                }
                finally { if (e_1) throw e_1.error; }
            }
        };
        SnapshotRenderer.prototype.copyNewHeadScriptElements = function () {
            var e_2, _a;
            try {
                for (var _b = __values(this.getNewHeadScriptElements()), _c = _b.next(); !_c.done; _c = _b.next()) {
                    var element = _c.value;
                    document.head.appendChild(this.createScriptElement(element));
                }
            }
            catch (e_2_1) { e_2 = { error: e_2_1 }; }
            finally {
                try {
                    if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                }
                finally { if (e_2) throw e_2.error; }
            }
        };
        SnapshotRenderer.prototype.removeCurrentHeadProvisionalElements = function () {
            var e_3, _a;
            try {
                for (var _b = __values(this.getCurrentHeadProvisionalElements()), _c = _b.next(); !_c.done; _c = _b.next()) {
                    var element = _c.value;
                    document.head.removeChild(element);
                }
            }
            catch (e_3_1) { e_3 = { error: e_3_1 }; }
            finally {
                try {
                    if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                }
                finally { if (e_3) throw e_3.error; }
            }
        };
        SnapshotRenderer.prototype.copyNewHeadProvisionalElements = function () {
            var e_4, _a;
            try {
                for (var _b = __values(this.getNewHeadProvisionalElements()), _c = _b.next(); !_c.done; _c = _b.next()) {
                    var element = _c.value;
                    document.head.appendChild(element);
                }
            }
            catch (e_4_1) { e_4 = { error: e_4_1 }; }
            finally {
                try {
                    if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                }
                finally { if (e_4) throw e_4.error; }
            }
        };
        SnapshotRenderer.prototype.relocateCurrentBodyPermanentElements = function () {
            var _this = this;
            return this.getCurrentBodyPermanentElements().reduce(function (placeholders, permanentElement) {
                var newElement = _this.newSnapshot.getPermanentElementById(permanentElement.id);
                if (newElement) {
                    var placeholder = createPlaceholderForPermanentElement(permanentElement);
                    replaceElementWithElement(permanentElement, placeholder.element);
                    replaceElementWithElement(newElement, permanentElement);
                    return __spread(placeholders, [placeholder]);
                }
                else {
                    return placeholders;
                }
            }, []);
        };
        SnapshotRenderer.prototype.replacePlaceholderElementsWithClonedPermanentElements = function (placeholders) {
            var e_5, _a;
            try {
                for (var placeholders_1 = __values(placeholders), placeholders_1_1 = placeholders_1.next(); !placeholders_1_1.done; placeholders_1_1 = placeholders_1.next()) {
                    var _b = placeholders_1_1.value, element = _b.element, permanentElement = _b.permanentElement;
                    var clonedElement = permanentElement.cloneNode(true);
                    replaceElementWithElement(element, clonedElement);
                }
            }
            catch (e_5_1) { e_5 = { error: e_5_1 }; }
            finally {
                try {
                    if (placeholders_1_1 && !placeholders_1_1.done && (_a = placeholders_1.return)) _a.call(placeholders_1);
                }
                finally { if (e_5) throw e_5.error; }
            }
        };
        SnapshotRenderer.prototype.activateNewBody = function () {
            document.adoptNode(this.newBody);
            this.activateNewBodyScriptElements();
        };
        SnapshotRenderer.prototype.activateNewBodyScriptElements = function () {
            var e_6, _a;
            try {
                for (var _b = __values(this.getNewBodyScriptElements()), _c = _b.next(); !_c.done; _c = _b.next()) {
                    var inertScriptElement = _c.value;
                    var activatedScriptElement = this.createScriptElement(inertScriptElement);
                    replaceElementWithElement(inertScriptElement, activatedScriptElement);
                }
            }
            catch (e_6_1) { e_6 = { error: e_6_1 }; }
            finally {
                try {
                    if (_c && !_c.done && (_a = _b.return)) _a.call(_b);
                }
                finally { if (e_6) throw e_6.error; }
            }
        };
        SnapshotRenderer.prototype.assignNewBody = function () {
            if (document.body) {
                replaceElementWithElement(document.body, this.newBody);
            }
            else {
                document.documentElement.appendChild(this.newBody);
            }
        };
        SnapshotRenderer.prototype.focusFirstAutofocusableElement = function () {
            var element = this.newSnapshot.findFirstAutofocusableElement();
            if (elementIsFocusable(element)) {
                element.focus();
            }
        };
        SnapshotRenderer.prototype.getNewHeadStylesheetElements = function () {
            return this.newHeadDetails.getStylesheetElementsNotInDetails(this.currentHeadDetails);
        };
        SnapshotRenderer.prototype.getNewHeadScriptElements = function () {
            return this.newHeadDetails.getScriptElementsNotInDetails(this.currentHeadDetails);
        };
        SnapshotRenderer.prototype.getCurrentHeadProvisionalElements = function () {
            return this.currentHeadDetails.getProvisionalElements();
        };
        SnapshotRenderer.prototype.getNewHeadProvisionalElements = function () {
            return this.newHeadDetails.getProvisionalElements();
        };
        SnapshotRenderer.prototype.getCurrentBodyPermanentElements = function () {
            return this.currentSnapshot.getPermanentElementsPresentInSnapshot(this.newSnapshot);
        };
        SnapshotRenderer.prototype.getNewBodyScriptElements = function () {
            return __spread(this.newBody.querySelectorAll("script"));
        };
        return SnapshotRenderer;
    }(Renderer));
    function createPlaceholderForPermanentElement(permanentElement) {
        var element = document.createElement("meta");
        element.setAttribute("name", "turbo-permanent-placeholder");
        element.setAttribute("content", permanentElement.id);
        return { element: element, permanentElement: permanentElement };
    }
    function replaceElementWithElement(fromElement, toElement) {
        var parentElement = fromElement.parentElement;
        if (parentElement) {
            return parentElement.replaceChild(toElement, fromElement);
        }
    }
    function elementIsFocusable(element) {
        return element && typeof element.focus == "function";
    }

    var View = (function () {
        function View(delegate) {
            this.htmlElement = document.documentElement;
            this.snapshotCache = new SnapshotCache(10);
            this.delegate = delegate;
        }
        View.prototype.getRootLocation = function () {
            return this.getSnapshot().getRootLocation();
        };
        View.prototype.getElementForAnchor = function (anchor) {
            return this.getSnapshot().getElementForAnchor(anchor);
        };
        View.prototype.getSnapshot = function () {
            return Snapshot.fromHTMLElement(this.htmlElement);
        };
        View.prototype.clearSnapshotCache = function () {
            this.snapshotCache.clear();
        };
        View.prototype.shouldCacheSnapshot = function () {
            return this.getSnapshot().isCacheable();
        };
        View.prototype.cacheSnapshot = function () {
            return __awaiter(this, void 0, void 0, function () {
                var snapshot, location_1;
                return __generator(this, function (_a) {
                    switch (_a.label) {
                        case 0:
                            if (!this.shouldCacheSnapshot()) return [3, 2];
                            this.delegate.viewWillCacheSnapshot();
                            snapshot = this.getSnapshot();
                            location_1 = this.lastRenderedLocation || Location.currentLocation;
                            return [4, nextMicrotask()];
                        case 1:
                            _a.sent();
                            this.snapshotCache.put(location_1, snapshot.clone());
                            _a.label = 2;
                        case 2: return [2];
                    }
                });
            });
        };
        View.prototype.getCachedSnapshotForLocation = function (location) {
            return this.snapshotCache.get(location);
        };
        View.prototype.render = function (_a, callback) {
            var snapshot = _a.snapshot, error = _a.error, isPreview = _a.isPreview;
            this.markAsPreview(isPreview);
            if (snapshot) {
                this.renderSnapshot(snapshot, isPreview, callback);
            }
            else {
                this.renderError(error, callback);
            }
        };
        View.prototype.scrollToAnchor = function (anchor) {
            var element = this.getElementForAnchor(anchor);
            if (element) {
                this.scrollToElement(element);
            }
            else {
                this.scrollToPosition({ x: 0, y: 0 });
            }
        };
        View.prototype.scrollToElement = function (element) {
            element.scrollIntoView();
        };
        View.prototype.scrollToPosition = function (_a) {
            var x = _a.x, y = _a.y;
            window.scrollTo(x, y);
        };
        View.prototype.markAsPreview = function (isPreview) {
            if (isPreview) {
                this.htmlElement.setAttribute("data-turbo-preview", "");
            }
            else {
                this.htmlElement.removeAttribute("data-turbo-preview");
            }
        };
        View.prototype.renderSnapshot = function (snapshot, isPreview, callback) {
            SnapshotRenderer.render(this.delegate, callback, this.getSnapshot(), snapshot, isPreview || false);
        };
        View.prototype.renderError = function (error, callback) {
            ErrorRenderer.render(this.delegate, callback, error || "");
        };
        return View;
    }());

    var Session = (function () {
        function Session() {
            this.navigator = new Navigator(this);
            this.history = new History(this);
            this.view = new View(this);
            this.adapter = new BrowserAdapter(this);
            this.pageObserver = new PageObserver(this);
            this.linkClickObserver = new LinkClickObserver(this);
            this.formSubmitObserver = new FormSubmitObserver(this);
            this.scrollObserver = new ScrollObserver(this);
            this.streamObserver = new StreamObserver(this);
            this.frameRedirector = new FrameRedirector(document.documentElement);
            this.enabled = true;
            this.progressBarDelay = 500;
            this.started = false;
        }
        Session.prototype.start = function () {
            if (!this.started) {
                this.pageObserver.start();
                this.linkClickObserver.start();
                this.formSubmitObserver.start();
                this.scrollObserver.start();
                this.streamObserver.start();
                this.frameRedirector.start();
                this.history.start();
                this.started = true;
                this.enabled = true;
            }
        };
        Session.prototype.disable = function () {
            this.enabled = false;
        };
        Session.prototype.stop = function () {
            if (this.started) {
                this.pageObserver.stop();
                this.linkClickObserver.stop();
                this.formSubmitObserver.stop();
                this.scrollObserver.stop();
                this.streamObserver.stop();
                this.frameRedirector.stop();
                this.history.stop();
                this.started = false;
            }
        };
        Session.prototype.registerAdapter = function (adapter) {
            this.adapter = adapter;
        };
        Session.prototype.visit = function (location, options) {
            if (options === void 0) { options = {}; }
            this.navigator.proposeVisit(Location.wrap(location), options);
        };
        Session.prototype.connectStreamSource = function (source) {
            this.streamObserver.connectStreamSource(source);
        };
        Session.prototype.disconnectStreamSource = function (source) {
            this.streamObserver.disconnectStreamSource(source);
        };
        Session.prototype.renderStreamMessage = function (message) {
            document.documentElement.appendChild(StreamMessage.wrap(message).fragment);
        };
        Session.prototype.clearCache = function () {
            this.view.clearSnapshotCache();
        };
        Session.prototype.setProgressBarDelay = function (delay) {
            this.progressBarDelay = delay;
        };
        Object.defineProperty(Session.prototype, "location", {
            get: function () {
                return this.history.location;
            },
            enumerable: false,
            configurable: true
        });
        Object.defineProperty(Session.prototype, "restorationIdentifier", {
            get: function () {
                return this.history.restorationIdentifier;
            },
            enumerable: false,
            configurable: true
        });
        Session.prototype.historyPoppedToLocationWithRestorationIdentifier = function (location) {
            if (this.enabled) {
                this.navigator.proposeVisit(location, { action: "restore", historyChanged: true });
            }
            else {
                this.adapter.pageInvalidated();
            }
        };
        Session.prototype.scrollPositionChanged = function (position) {
            this.history.updateRestorationData({ scrollPosition: position });
        };
        Session.prototype.willFollowLinkToLocation = function (link, location) {
            return this.elementIsNavigable(link)
                && this.locationIsVisitable(location)
                && this.applicationAllowsFollowingLinkToLocation(link, location);
        };
        Session.prototype.followedLinkToLocation = function (link, location) {
            var action = this.getActionForLink(link);
            this.visit(location, { action: action });
        };
        Session.prototype.allowsVisitingLocation = function (location) {
            return this.applicationAllowsVisitingLocation(location);
        };
        Session.prototype.visitProposedToLocation = function (location, options) {
            this.adapter.visitProposedToLocation(location, options);
        };
        Session.prototype.visitStarted = function (visit) {
            this.notifyApplicationAfterVisitingLocation(visit.location);
        };
        Session.prototype.visitCompleted = function (visit) {
            this.notifyApplicationAfterPageLoad(visit.getTimingMetrics());
        };
        Session.prototype.willSubmitForm = function (form, submitter) {
            return this.elementIsNavigable(form) && this.elementIsNavigable(submitter);
        };
        Session.prototype.formSubmitted = function (form, submitter) {
            this.navigator.submitForm(form, submitter);
        };
        Session.prototype.pageBecameInteractive = function () {
            this.view.lastRenderedLocation = this.location;
            this.notifyApplicationAfterPageLoad();
        };
        Session.prototype.pageLoaded = function () {
            this.history.assumeControlOfScrollRestoration();
        };
        Session.prototype.pageWillUnload = function () {
            this.history.relinquishControlOfScrollRestoration();
        };
        Session.prototype.receivedMessageFromStream = function (message) {
            this.renderStreamMessage(message);
        };
        Session.prototype.viewWillRender = function (newBody) {
            this.notifyApplicationBeforeRender(newBody);
        };
        Session.prototype.viewRendered = function () {
            this.view.lastRenderedLocation = this.history.location;
            this.notifyApplicationAfterRender();
        };
        Session.prototype.viewInvalidated = function () {
            this.adapter.pageInvalidated();
        };
        Session.prototype.viewWillCacheSnapshot = function () {
            this.notifyApplicationBeforeCachingSnapshot();
        };
        Session.prototype.applicationAllowsFollowingLinkToLocation = function (link, location) {
            var event = this.notifyApplicationAfterClickingLinkToLocation(link, location);
            return !event.defaultPrevented;
        };
        Session.prototype.applicationAllowsVisitingLocation = function (location) {
            var event = this.notifyApplicationBeforeVisitingLocation(location);
            return !event.defaultPrevented;
        };
        Session.prototype.notifyApplicationAfterClickingLinkToLocation = function (link, location) {
            return dispatch("turbo:click", { target: link, detail: { url: location.absoluteURL }, cancelable: true });
        };
        Session.prototype.notifyApplicationBeforeVisitingLocation = function (location) {
            return dispatch("turbo:before-visit", { detail: { url: location.absoluteURL }, cancelable: true });
        };
        Session.prototype.notifyApplicationAfterVisitingLocation = function (location) {
            return dispatch("turbo:visit", { detail: { url: location.absoluteURL } });
        };
        Session.prototype.notifyApplicationBeforeCachingSnapshot = function () {
            return dispatch("turbo:before-cache");
        };
        Session.prototype.notifyApplicationBeforeRender = function (newBody) {
            return dispatch("turbo:before-render", { detail: { newBody: newBody } });
        };
        Session.prototype.notifyApplicationAfterRender = function () {
            return dispatch("turbo:render");
        };
        Session.prototype.notifyApplicationAfterPageLoad = function (timing) {
            if (timing === void 0) { timing = {}; }
            return dispatch("turbo:load", { detail: { url: this.location.absoluteURL, timing: timing } });
        };
        Session.prototype.getActionForLink = function (link) {
            var action = link.getAttribute("data-turbo-action");
            return isAction(action) ? action : "advance";
        };
        Session.prototype.elementIsNavigable = function (element) {
            var container = element === null || element === void 0 ? void 0 : element.closest("[data-turbo]");
            if (container) {
                return container.getAttribute("data-turbo") != "false";
            }
            else {
                return true;
            }
        };
        Session.prototype.locationIsVisitable = function (location) {
            return location.isPrefixedBy(this.view.getRootLocation()) && location.isHTML();
        };
        return Session;
    }());

    var session = new Session;
    var navigator = session.navigator;
    function start() {
        session.start();
    }
    function registerAdapter(adapter) {
        session.registerAdapter(adapter);
    }
    function visit(location, options) {
        session.visit(location, options);
    }
    function connectStreamSource(source) {
        session.connectStreamSource(source);
    }
    function disconnectStreamSource(source) {
        session.disconnectStreamSource(source);
    }
    function renderStreamMessage(message) {
        session.renderStreamMessage(message);
    }
    function clearCache() {
        session.clearCache();
    }
    function setProgressBarDelay(delay) {
        session.setProgressBarDelay(delay);
    }

    start();

    exports.clearCache = clearCache;
    exports.connectStreamSource = connectStreamSource;
    exports.disconnectStreamSource = disconnectStreamSource;
    exports.navigator = navigator;
    exports.registerAdapter = registerAdapter;
    exports.renderStreamMessage = renderStreamMessage;
    exports.setProgressBarDelay = setProgressBarDelay;
    exports.start = start;
    exports.visit = visit;

    Object.defineProperty(exports, '__esModule', { value: true });

})));
//# sourceMappingURL=turbo.es5-umd.js.map
